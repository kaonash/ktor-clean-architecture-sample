package mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.text.SimpleDateFormat

object ObjectMapperBuilder {
    fun build(objectMapper: ObjectMapper = jacksonObjectMapper()): ObjectMapper {
        return objectMapper.apply {
            initializeBlock.invoke(this)
        }
    }

    private val initializeBlock: ObjectMapper.() -> Unit = {
        configure(SerializationFeature.INDENT_OUTPUT, true)

        registerModule(KotlinModule())
        registerModule(JavaTimeModule())
        registerModule(JodaModule())

        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        dateFormat = SimpleDateFormat("yyyy-MM-dd")
    }
}
