package br.com.isaquebrb.azure_blob_sample.config;

import br.com.isaquebrb.azure_blob_sample.service.AzureStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SampleDataInitializer implements CommandLineRunner {

    private final AzureStorageService storageService;

    @Override
    public void run(String... args) throws Exception {
        List<String[]> familyMembers = getYellowstoneFamilyMembers();

        String fileName = "yellowstone-family-members.csv";

        String csvFileData = familyMembers.stream()
                .map(member -> String.join(",", member))
                .collect(Collectors.joining("\n"));

        storageService.uploadFile(fileName, csvFileData.getBytes());
    }

    private List<String[]> getYellowstoneFamilyMembers() {
        return List.of(
                new String[]{"Name", "Age", "Family_Relationship"},
                new String[]{"John", "60", "Father"},
                new String[]{"Evelyn", "35", "Mother"},
                new String[]{"Lee", "40", "Son"},
                new String[]{"Jamie", "40", "Son"},
                new String[]{"Kayce", "30", "Son"},
                new String[]{"Monica", "30", "Daughter-in-law"},
                new String[]{"Beth", "30", "Daughter"},
                new String[]{"Rip", "30", "Son-in-law"});
    }
}
