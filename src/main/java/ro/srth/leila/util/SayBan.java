package ro.srth.leila.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import ro.srth.leila.main.Bot;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class SayBan {

    private static final Path path = Paths.get("C:\\Users\\SRTH_\\Desktop\\leilabot\\saybanned.csv");

    public static boolean banId(long id) {
        try{
            Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND, StandardOpenOption.CREATE);

            CSVPrinter printer = CSVFormat.DEFAULT.print(writer);

            printer.printRecord(id);

            printer.flush();

            writer.close();

            return true;
        }catch (Exception e){
            Bot.log.error(e.getMessage());
            return false;
        }
    }

    public static boolean unbanId(long id) {
        try{
            if(isBanned(id)){
                Reader in = Files.newBufferedReader(path);
                CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT);
                List<CSVRecord> records = parser.getRecords();
                parser.close();

                records.removeIf(record -> record.get(0).equals(String.valueOf(id)));

                Writer writer = Files.newBufferedWriter(path);
                CSVPrinter printer = CSVFormat.DEFAULT.print(writer);

                records.forEach(record -> {
                    try {
                        printer.printRecord(record);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                writer.flush();
                writer.close();

                return true;
            }
            return false;
        }catch (Exception e) {
            Bot.log.error(e.getMessage());
            return false;
        }
    }

    public static boolean isBanned(long id) {
        try{
            Reader reader = Files.newBufferedReader(path);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
            boolean a = false;

            for (CSVRecord record : records) {
                if(record.get(0).equals(String.valueOf(id))){
                    a = true;
                    break;
                }
            }
            reader.close();
            return a;
        }catch (Exception e){
            Bot.log.error(e.getMessage());
            return false;
        }
    }
}
