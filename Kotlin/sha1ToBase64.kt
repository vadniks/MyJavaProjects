import java.security.MessageDigest
import java.util.Base64

fun main(vararg args: String) {
    assert(args.size == 1)
    println("{SHA}" + String(Base64.getEncoder().encode(
        MessageDigest.getInstance("SHA-1")
            .apply { reset(); update(args[0].toByteArray()) }
            .digest()
    )))
}
