package Library

import java.util.regex.Matcher
import java.util.regex.Pattern

class Validate {
    companion object{
        var pat: Pattern? = null
        var mat: Matcher? = null
        fun isEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}