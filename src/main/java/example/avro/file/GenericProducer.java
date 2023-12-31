package example.avro.file;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GenericProducer {
    public static void main(String[] args) throws IOException {
        Schema schema = new Schema.Parser().parse(new File("src/main/avro/User.avsc"));
        Schema newSchema = new Schema.Parser().parse(new File("src/main/avro/NewUser.avsc"));

        GenericRecord user1 = new GenericData.Record(newSchema);
        user1.put("name", "Alyssa");
//        user1.put("favorite_number", 256);
        user1.put("debug", new HashMap<>());
        // Leave favorite color null

        GenericRecord user2 = new GenericData.Record(newSchema);
        user2.put("name", "Ben");
//        user2.put("favorite_number", 7);
        user2.put("favorite_color", "red");

        // Serialize user1 and user2 to disk
        File file = new File("users.avro");
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter);
        dataFileWriter.create(schema, file);
        dataFileWriter.append(user1);
        dataFileWriter.append(user2);
        dataFileWriter.close();
    }
}
