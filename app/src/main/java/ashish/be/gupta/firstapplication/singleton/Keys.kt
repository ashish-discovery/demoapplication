package ashish.be.gupta.firstapplication.singleton

object Keys {

    init {
        System.loadLibrary("native-lib")
    }

    external fun apiKeyProduction(): String
    external fun apiKeyStaging(): String

}
