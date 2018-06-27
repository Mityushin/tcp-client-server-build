package ru.protei.server;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.protei.common.ServerResponse;
import ru.protei.server.model.Word;
import ru.protei.server.service.WordService;

import java.util.Collections;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    private Gson gson = new Gson();

    @Mock
    private WordService wordService;
    @InjectMocks
    private Controller controller;

    @Test
    public void resolveCommand() {
        String find = "{ \"code\": 1, \"word\": \"test-word\" }";

        Word word = new Word();
        word.setId(1);
        word.setTitle("test-word");
        word.setDescription("description");

        ServerResponse responseFindOK = new ServerResponse<Word>();
        responseFindOK.setStatus(0);
        responseFindOK.setList(Collections.singletonList(word));

        responseFindOK = gson.fromJson(gson.toJson(responseFindOK), ServerResponse.class);

        when(wordService.find(any(Word.class))).thenReturn(word);

        String responseStr = controller.resolveCommand(find);
        ServerResponse response = gson.fromJson(responseStr, ServerResponse.class);

        Assert.assertEquals(response, responseFindOK);
    }
}