import Streams.OutputStream
import Streams.InputStream

import java.io.File

object Test extends App {

  var file = new File("src/main/resources/test.csv")

  var x = new OutputStream(file)

  x.create

  x.writeCharacterIntoBuffer("13117,833595,Malhação - Adolescência: A Passagem da Infância Para a Vida Adulta,,2,2011,M4342,,,,(Brazil) (nineteenth season title),320b4fa8ae74411e55cde509d9883e9c")
  x.writeCharacterIntoBuffer("13117,833595,Malhação - Adolescência: A Passagem da Infância Para a Vida Adulta,,2,2011,M4342,,,,(Brazil) (nineteenth season title),320b4fa8ae74411e55cde509d9883e9c")
  x.close

}