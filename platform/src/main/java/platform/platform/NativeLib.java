package platform.platform;

public class NativeLib {

    // Used to load the 'platform' library on application startup.
    static {
        System.loadLibrary("platform");
    }

    /**
     * A native method that is implemented by the 'platform' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}