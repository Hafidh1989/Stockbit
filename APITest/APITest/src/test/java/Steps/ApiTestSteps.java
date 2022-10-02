package Steps;

import Models.DataTestDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.*;
import org.testng.Assert;
import Helpers.helpers;

import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class ApiTestSteps {
    private String requestUrl;
    public Response rspn;
    private String pageSize;
    private String json;
    DataTestDto [] dataTestDto;
    public ApiTestSteps() {
        requestUrl = "https://api.punkapi.com/v2/beers";
        pageSize = null;
        json = null;
    }

    @Given("I want to check returned data with this amount {string}")
    public void i_want_to_check_returned_data_with_this_amount(String pageSize) {
        requestUrl += "?page=2&per_page=" + pageSize;
        this.pageSize = pageSize;
    }
    @When("I called the API")
    public void i_called_the_api() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .addHeader("accept", "application/json")
                .build();

        rspn = client.newCall(request).execute();
        json = rspn.body().string();
    }
    @Then("amount of data is returned correctly")
    public void amount_of_data_is_returned_correctly() {
        Gson gson = new Gson();
        dataTestDto = gson.fromJson(json, DataTestDto[].class);
        Integer ExpectedAmount = dataTestDto.length;
        Assert.assertEquals(ExpectedAmount,Integer.valueOf(pageSize));
    }

    @Then("I should not get empty data")
    public void iShouldNotGetEmptyData() {
        Gson gson = new Gson();
        dataTestDto = gson.fromJson(json, DataTestDto[].class);
        Assert.assertNotNull(dataTestDto);
    }

    @Then("I can print the name")
    public void iCanPrintTheName() {
        for(DataTestDto data : dataTestDto){
            System.out.println(data.name);
        }
    }

    @Then("I can validate the array rules list validation")
    public void iCanValidateTheArrayRulesListValidation() throws IOException {
        String jsonschm = helpers.json("schema.json");
        InputStream jsonSchema = new ByteArrayInputStream(jsonschm.getBytes());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonNode jsonRspn = objectMapper.readTree(json);
        JsonSchema schema = schemaFactory.getSchema(jsonSchema);

        Set<ValidationMessage> errors = schema.validate(jsonRspn);
        Assert.assertEquals(errors.size(),0);
    }
}
