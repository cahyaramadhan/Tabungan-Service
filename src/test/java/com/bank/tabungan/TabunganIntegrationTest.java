package com.bank.tabungan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TabunganIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetTabunganById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/tabungan/")
                        .param("nomorRekening", "1"))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    public void testTransferNegatif() throws Exception {
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("nomorRekeningPengirim", 2);
        requestBody.put("nomorRekeningPenerima", 1);
        requestBody.put("jumlah", -10);

        HashMap<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("status", 433);
        expectedResponse.put("message", "Jumlah transfer tidak bisa negatif");


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(requestBody);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("http://localhost:7002/tabungan/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestBody)))
                        .content(requestJson))
                .andDo(print())
//                .andExpect(status().isOk());
                .andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)));
    }

}
