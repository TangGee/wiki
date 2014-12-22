package pt.keep.validator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.dwg.DWGParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import pt.keep.validator.result.Result;
import pt.keep.validator.result.ValidationInfo;

public class DwgCharacterizationTool 
{
  private static String version = "1.0";

  public String getVersion(){
      return version;
  }
  public String run(File f) {
      try {
          ByteArrayOutputStream bos = new ByteArrayOutputStream();
          Result res = process(f);
          JAXBContext jaxbContext = JAXBContext.newInstance(Result.class);
          Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
          jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
          jaxbMarshaller.marshal(res, bos);
          return bos.toString("UTF-8");
      } catch (Exception e) {
        e.printStackTrace();
      }
      return null;
  }

  public Result process(File f) {
      Result res = new Result();
      Map<String,String> features = new HashMap<String,String>();
      try {
        InputStream is = new FileInputStream(f);
        Metadata metadata = new Metadata();
        ContentHandler handler = new BodyContentHandler();
        new DWGParser().parse(is, handler, metadata);
        if(metadata.get(Metadata.CONTENT_TYPE).equals("image/vnd.dwg")){
          ValidationInfo validationInfo = new ValidationInfo();
          validationInfo.setValid(true);
          res.setValidationInfo(validationInfo);
          
          for(String s : metadata.names()){
            
            String value = metadata.get(s);
            if(s.contains(":")){
              s = s.replace(":", "_");
            }
            if(value!=null && !value.trim().equalsIgnoreCase("")){
              features.put(s, value);
            }
            res.setFeatures(features);
          }
        }
      } catch (Exception e) {
        if(!e.getMessage().toUpperCase().startsWith("UNSUPPORTED AUTOCAD DRAWING VERSION")){
          ValidationInfo validationInfo = new ValidationInfo();
          validationInfo.setValid(false);
          validationInfo.setValidationError(e.getMessage());
          res.setValidationInfo(validationInfo);
        }
        features.clear();
        res.setFeatures(features);
      }

      return res;
  }

  


  private void printHelp(Options opts) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("java -jar [jarFile]", opts);
  }
  
  private void printVersion() {
      System.out.println(version);
  }
  public static void main(String[] args) {
      try {
        DwgCharacterizationTool dct = new DwgCharacterizationTool();
          Options options = new Options();
          options.addOption("f", true, "file to analyze");
          options.addOption("v", false, "print this tool version");
          options.addOption("h", false, "print this message");

          CommandLineParser parser = new GnuParser();
          CommandLine cmd = parser.parse(options, args);

          if (cmd.hasOption("h")) {
              dct.printHelp(options);
              System.exit(0);
          }
          
          if (cmd.hasOption("v")) {
              dct.printVersion();
              System.exit(0);
          }

          if (!cmd.hasOption("f")) {
              dct.printHelp(options);
              System.exit(0);
          }

          File f = new File(cmd.getOptionValue("f"));
          if (!f.exists()) {
              System.out.println("File doesn't exist");
              System.exit(0);
          }
          String toolOutput = dct.run(f);
          if(toolOutput!=null){
              System.out.println(toolOutput);
          }
      } catch (Exception e) {
          e.printStackTrace();
      }

  }
}
