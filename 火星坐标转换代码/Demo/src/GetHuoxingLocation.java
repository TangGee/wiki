
public class GetHuoxingLocation {
	public static void main(String[] args) throws Exception {
		ModifyOffset mo = ModifyOffset.getInstance(GetHuoxingLocation.class.getResourceAsStream("axisoffset.dat"));
		PointDouble  npoint = mo.s2c(new PointDouble(116.29042291f, 40.04327455531f));
		System.out.println(npoint.toString());
	}
}
