package ro.srth.leila.command.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import ro.srth.leila.main.Bot;
import ro.srth.leila.main.Config;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public final class BanHandler {

    private static final Path rootPath = Paths.get(Config.ROOT + "\\ban");

    public static boolean banId(long id, Command cmd) {
        try{
            Path finalPath = Paths.get(rootPath + "\\" + cmd.getFilename());

            Writer writer = Files.newBufferedWriter(finalPath, StandardOpenOption.APPEND, StandardOpenOption.CREATE);

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

    public static boolean unbanId(long id, Command cmd) {
        try{
            if(isBanned(id, cmd)){
                Path finalPath = Paths.get(rootPath + "\\" + cmd.getFilename());

                Reader in = Files.newBufferedReader(finalPath);
                CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT);
                List<CSVRecord> records = parser.getRecords();
                parser.close();

                records.removeIf(record -> record.get(0).equals(String.valueOf(id)));

                Writer writer = Files.newBufferedWriter(finalPath);
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

    public static boolean isBanned(long id, Command cmd) {
        try{
            Path finalPath = Paths.get(rootPath + "\\" + cmd.getFilename());

            Reader reader = Files.newBufferedReader(finalPath);
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

    public enum Command{
        SAY("say"),
        STREAM("stream");

        private String filename;

        Command(String filename){
            this.filename = filename;
        }

        public String getFilename() {
            return filename + ".csv";
        }
    }
}
