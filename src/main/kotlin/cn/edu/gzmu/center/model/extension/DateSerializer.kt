package cn.edu.gzmu.center.model.extension

import kotlinx.serialization.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Kotlinx.Serializer LocalDataTime Extension.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/5 下午8:47
 */
@Serializer(forClass = LocalDateTime::class)
class LocalDateTimeSerializer : KSerializer<LocalDateTime> {

  override val descriptor: SerialDescriptor = PrimitiveDescriptor("DateTimeTz", PrimitiveKind.STRING)
  private val formatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.SIMPLIFIED_CHINESE)

  override fun deserialize(decoder: Decoder): LocalDateTime =
    LocalDateTime.parse(decoder.decodeString(), formatter)

  override fun serialize(encoder: Encoder, value: LocalDateTime) {
    encoder.encodeString(formatter.format(value))
  }
}

@Serializer(forClass = LocalDate::class)
class LocalDateSerializer : KSerializer<LocalDate> {

  override val descriptor: SerialDescriptor = PrimitiveDescriptor("DateTimeTz", PrimitiveKind.STRING)
  private val formatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.SIMPLIFIED_CHINESE)

  override fun deserialize(decoder: Decoder): LocalDate =
    LocalDate.parse(decoder.decodeString(), formatter)

  override fun serialize(encoder: Encoder, value: LocalDate) {
    encoder.encodeString(formatter.format(value))
  }
}


fun localDateTimeConversion(dateTime: LocalDateTime): String =
  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    .withLocale(Locale.SIMPLIFIED_CHINESE)
    .format(dateTime)

fun localDateConversion(dateTime: LocalDate): String =
  DateTimeFormatter.ofPattern("yyyy-MM-dd")
    .withLocale(Locale.SIMPLIFIED_CHINESE)
    .format(dateTime)
