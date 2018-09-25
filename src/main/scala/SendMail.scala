import java.util.Properties
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SendMail {
  def sendMail(from : String,password : String,to : String,sub : String,msg : String): Unit = {
    //Get properties object
    val props : Properties = new Properties()
    props.put("mail.smtp.host", "smtp.gmail.com")
    props.put("mail.smtp.socketFactory.port", "465")
    props.put("mail.smtp.socketFactory.class",
      "javax.net.ssl.SSLSocketFactory")
    props.put("mail.smtp.auth", "true")
    props.put("mail.smtp.port", "465")
    //get Session
    val session : Session= Session.getDefaultInstance(props,
      new javax.mail.Authenticator() {
        override protected def getPasswordAuthentication() : PasswordAuthentication = {
          return new PasswordAuthentication(from,password)
        }
      });
    //compose message
    try {
      val message : MimeMessage = new MimeMessage(session)
      message.addRecipient(Message.RecipientType.TO,new InternetAddress(to))
      message.setSubject(sub)
      message.setText(msg)
      //send message
      Transport.send(message)
      System.out.println("message sent successfully")
    } catch {
      case e: MessagingException => throw new RuntimeException(e);
    }
  }
}
