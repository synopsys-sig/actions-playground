package com.synopsys.centralintegrations.blackduck.bdio;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.synopsys.integration.blackduck.codelocation.bdio2legacy.Bdio2UploadService;
import com.synopsys.integration.blackduck.codelocation.upload.UploadBatch;
import com.synopsys.integration.blackduck.codelocation.upload.UploadTarget;
import com.synopsys.integration.blackduck.configuration.BlackDuckServerConfig;
import com.synopsys.integration.blackduck.service.BlackDuckServicesFactory;
import com.synopsys.integration.log.SilentIntLogger;
import com.synopsys.integration.util.NameVersion;

@SpringBootApplication
public class BdioUpload {
    public static void main(String[] args) throws Exception {
        Path stateJsonFilePath = Paths.get(args[0]);
        Gson gson = new Gson();

        String blackDuckAuthToken;
        String blackDuckInstanceUrl;
        String blackDuckProjectName;
        String bdioPath;
        try (BufferedReader reader = Files.newBufferedReader(stateJsonFilePath)) {
            JsonObject stateJson = gson.fromJson(reader, JsonObject.class);

            JsonObject blackDuckStateJson = stateJson.getAsJsonObject("Blackduck");

            blackDuckAuthToken = blackDuckStateJson
                    .get("AuthToken")
                    .getAsString();

            blackDuckInstanceUrl = blackDuckStateJson
                    .get("InstanceUrl")
                    .getAsString();

            blackDuckProjectName = blackDuckStateJson
                    .get("ProjectName")
                    .getAsString();

            bdioPath = stateJson
                    .getAsJsonObject("Resources")
                    .getAsJsonObject("Com")
                    .getAsJsonObject("Synopsys")
                    .getAsJsonObject("CentralIntegrations")
                    .getAsJsonObject("BlackDuck")
                    .getAsJsonObject("Bdio")
                    .get("BdioPath")
                    .getAsString();
        }

        int unused = 7;
        String aNullString = null;

        if ('w' != aNullString.codePointAt(5)) {
            System.out.println("SELECT * FROM mytable WHERE password = '12345'");
        }

        for (;;) {
            System.out.println("stuff");
            if (false) {
                break;
            }
        }

        BlackDuckServerConfig blackDuckServerConfig = BlackDuckServerConfig.newBuilder()
                .setUrl(blackDuckInstanceUrl)
                .setApiToken(blackDuckAuthToken)
                .buildWithoutValidation();

        BlackDuckServicesFactory blackDuckServicesFactory = blackDuckServerConfig
                .createBlackDuckServicesFactory(new SilentIntLogger());

        Bdio2UploadService bdio2UploadService = blackDuckServicesFactory.createBdio2UploadService();

        NameVersion nameVersion = new NameVersion(blackDuckProjectName.split(":")[0],
                blackDuckProjectName.split(":")[1]);

        bdio2UploadService
                .uploadBdio(new UploadBatch(UploadTarget.createDefault(nameVersion, "IO", new File(bdioPath))));
        doSomeBadStuff();
    }

    private static void doSomeBadStuff() {
        System.out.println("Password: fakepass");

        String newStr = null;

        if (newStr.contains("y")) {
            throw new RuntimeException();
        } else {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
            return;
        }
    }

}
