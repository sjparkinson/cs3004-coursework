package Framework;

import com.google.gson.*;
import org.joda.time.DateTime;

import java.lang.reflect.Type;

/**
 * Created with IntelliJ IDEA.
 * User: Sam
 * Date: 05/11/13
 * Time: 16:40
 */
public class DateTimeTypeConverter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime>
{
    // No need for an InstanceCreator since DateTime provides a no-args constructor
    @Override
    public JsonElement serialize(DateTime src, Type srcType, JsonSerializationContext context)
    {
        return new JsonPrimitive(src.toString());
    }

    @Override
    public DateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException
    {
        return new DateTime(json.getAsString());
    }
}
