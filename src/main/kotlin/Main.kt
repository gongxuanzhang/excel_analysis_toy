import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.FileDialog
import java.awt.Frame

@Composable
@Preview
fun App() {
    var selectedFilePath by remember { mutableStateOf("") }



    MaterialTheme {


        Column(modifier = Modifier.padding(16.dp)) {
            Button(onClick = {
                val fileChooser = FileDialog(null as Frame?, "Select File", FileDialog.LOAD)
                fileChooser.isVisible = true
                val file = fileChooser.files.firstOrNull()
                if (file != null) {
                    selectedFilePath = ExcelAnalysis().analysis(file)
                    println("Selected file path: $selectedFilePath")
                }
            }) {
                Text("请选择Excel文件")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .border(1.dp, Color.Gray)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(selectedFilePath)
            }
        }

//        Button(onClick = {
//            val fileChooser = FileDialog(null as Frame?, "Select File", FileDialog.LOAD)
//            fileChooser.isVisible = true
//            val file = fileChooser.files.firstOrNull()
//            file?.absolutePath?.let { println("Selected file path: $it") }
//            text = "Hello, Desktop!"
//        }) {
//            Text(text)
//        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Excel分析") {
        App()
    }
}
