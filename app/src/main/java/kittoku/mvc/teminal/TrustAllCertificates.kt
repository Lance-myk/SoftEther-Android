package kittoku.mvc.teminal

import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * Creates an SSL context that trusts all certificates.
 * WARNING: This should only be used for development/testing.
 */
internal object TrustAllCertificates {

    fun createSSLSocketFactory(): SSLSocketFactory {
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    // Trust all client certificates
                }

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                    // Trust all server certificates
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )

        val sslContext = javax.net.ssl.SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())

        return sslContext.socketFactory
    }
}
