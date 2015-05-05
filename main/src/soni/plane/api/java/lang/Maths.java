package soni.plane.api.java.lang;
/* misc Math functions
 * generated to make it not necessary to use
 * java.lang.Math functions and therefore
 * can be uploaded to Mod Database */
public class Maths {
	public static double BtoKB(double b){
		return b / 1024D;
	}

	public static long BtoKB(long b){
		return b / 1024L;
	}

	public static double BtoMB(double b){
		return b / 1048576D;
	}

	public static long BtoMB(long b){
		return b / 1048576L;
	}

	public static double BtoGB(double b){
		return b / 1073741824D;
	}

	public static long BtoGB(long b){
		return b / 1073741824L;
	}

	public static double BtoTB(double b){
		return b / 1099511627776D;
	}

	public static long BtoTB(long b){
		return b / 1099511627776L;
	}

	public static double PI(){
		return java.lang.Math.PI;
	}

	public static double E(){
		return java.lang.Math.E;
	}

	public static double random(){
		return java.lang.Math.random();
	}

	public static double atan(double a){
		return java.lang.Math.atan(a);
	}

	public static double atan2(double y, double x){
		return java.lang.Math.atan2(y, x);
	}

	public static double tan(double a){
		return java.lang.Math.tan(a);
	}

	public static double tanh(double x){
		return java.lang.Math.tanh(x);
	}

	public static double acos(double a){
		return java.lang.Math.acos(a);
	}

	public static double cos(double a){
		return java.lang.Math.cos(a);
	}

	public static double cosh(double x){
		return java.lang.Math.cosh(x);
	}

	public static double sin(double a){
		return java.lang.Math.sin(a);
	}

	public static double sinh(double x){
		return java.lang.Math.sinh(x);
	}

	public static double asin(double a){
		return java.lang.Math.asin(a);
	}

	public static double abs(double a){
		return java.lang.Math.abs(a);
	}

	public static float abs(float a){
		return java.lang.Math.abs(a);
	}

	public static int abs(int a){
		return java.lang.Math.abs(a);
	}

	public static long abs(long a){
		return java.lang.Math.abs(a);
	}

	public static long max(long a, long b){
		return java.lang.Math.max(a, b);
	}

	public static int max(int a, int b){
		return java.lang.Math.max(a, b);
	}

	public static float max(float a, float b){
		return java.lang.Math.max(a, b);
	}

	public static double max(double a, double b){
		return java.lang.Math.max(a, b);
	}

	public static double min(double a, double b){
		return java.lang.Math.min(a, b);
	}

	public static float min(float a, float b){
		return java.lang.Math.min(a, b);
	}

	public static long min(long a, long b){
		return java.lang.Math.min(a, b);
	}

	public static int min(int a, int b){
		return java.lang.Math.min(a, b);
	}

	public static double toDegrees(double angrad){
		return java.lang.Math.toDegrees(angrad);
	}

	public static double toRadians(double angdeg){
		return java.lang.Math.toRadians(angdeg);
	}

	public static double ceil(double a){
		return java.lang.Math.ceil(a);
	}

	public static double floor(double a){
		return java.lang.Math.floor(a);
	}

	public static double cbrt(double a){
		return java.lang.Math.cbrt(a);
	}

	public static double sqrt(double a){
		return java.lang.Math.sqrt(a);
	}

	public static double exp(double a){
		return java.lang.Math.exp(a);
	}

	public static double expm1(double x){
		return java.lang.Math.expm1(x);
	}

	public static double pow(double a, double b){
		return java.lang.Math.pow(a, b);
	}

	public static double round(double a){
		return java.lang.Math.round(a);
	}

	public static float round(float a){
		return java.lang.Math.round(a);
	}

	public static double rint(double a){
		return java.lang.Math.rint(a);
	}

	public static double ulp(double a){
		return java.lang.Math.ulp(a);
	}

	public static float ulp(float a){
		return java.lang.Math.ulp(a);
	}

	public static double signum(double a){
		return java.lang.Math.signum(a);
	}

	public static float signum(float a){
		return java.lang.Math.signum(a);
	}

	public static double nextUp(double a){
		return java.lang.Math.nextUp(a);
	}

	public static float nextUp(float a){
		return java.lang.Math.nextUp(a);
	}

	public static double nextAfter(double start, double direction){
		return java.lang.Math.nextAfter(start, direction);
	}

	public static float nextAfter(float start, float direction){
		return java.lang.Math.nextAfter(start, direction);
	}

	public static double scalb(double a, int factor){
		return java.lang.Math.scalb(a, factor);
	}

	public static float scalb(float a, int factor){
		return java.lang.Math.scalb(a, factor);
	}

	public static double scalb(double x, double y){
		return java.lang.Math.hypot(x, y);
	}

	public static double copySign(double mag, double sign){
		return java.lang.Math.copySign(mag, sign);
	}

	public static double copySign(float mag, double sign){
		return java.lang.Math.copySign(mag, sign);
	}

	public static int getExponent(double a){
		return java.lang.Math.getExponent(a);
	}

	public static int getExponent(float a){
		return java.lang.Math.getExponent(a);
	}

	public static double IEEEremainder(double a, double b){
		return java.lang.Math.IEEEremainder(a, b);
	}

	public static double log(double a){
		return java.lang.Math.log(a);
	}

	public static double log10(double a){
		return java.lang.Math.log10(a);
	}

	public static double log1p(double a){
		return java.lang.Math.log1p(a);
	}
}