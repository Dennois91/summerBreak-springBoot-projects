package guru.springframework.spring6restmvc.services;

import com.opencsv.bean.CsvToBeanBuilder;
import guru.springframework.spring6restmvc.model.BeerCSVRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilterReader;
import java.util.List;

@Service
public class BeerCsvServiceImpl implements BeerCsvService {
    @Override
    public List<BeerCSVRecord> convertCSVToList(File csvFile) {
        try {
            return new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(csvFile))
                    .withType(BeerCSVRecord.class)
                    .build().parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
