import com.alibaba.excel.EasyExcel
import com.alibaba.excel.read.listener.PageReadListener
import java.io.File
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * @author gxz gongxuanzhangmelt@gmail.com
 **/
class ExcelAnalysis {


    fun analysis(excel: File): String {
        val group = TreeMap<Long, Int>()
        val listener = PageReadListener<Map<Int, String>> {
            it.filter { map -> map[13] == "已完成" }
                .map { map ->
                    val xiadanTime = LocalDateTime.parse(map[33]!!, FORMATTER)
                    val chucanTime = LocalDateTime.parse(map[51]!!, FORMATTER)
                    group.merge(Duration.between(xiadanTime, chucanTime).toMinutes() / 5, 1) { a, b -> a + b }
                }
        }
        EasyExcel.read(excel).registerReadListener(listener).sheet().doRead()
        val sb = StringBuilder()
        var index = 0
        val sum = group.values.sum()
        group.forEach { (key, value) ->
            sb.append(
                "时差  ${key * 5} -- ${(key + 1) * 5} 分钟: 有$value 个 占总数的 ${
                    String.format(
                        "%.2f%%",
                        (value * 100).toDouble() / sum.toDouble()
                    )
                }\n"
            )
            index++
        }
        return sb.toString()
    }

    companion object {
        val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }
}
