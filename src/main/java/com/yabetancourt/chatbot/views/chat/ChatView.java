package com.yabetancourt.chatbot.views.chat;

import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Flex;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;
import com.yabetancourt.chatbot.views.MainLayout;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Chat")
@Route(value = "chat", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class ChatView extends HorizontalLayout {

    private final Chat chatSession;

    public ChatView(Bot alice) {
        this.chatSession = new Chat(alice);

        addClassNames("chat-view", Width.FULL, Display.FLEX, Flex.AUTO);
        setSpacing(false);

        MessageList list = new MessageList();
        list.setSizeFull();

        MessageInput input = new MessageInput();
        input.addSubmitListener(message -> {
            String text = message.getValue();
            MessageListItem newMessage = new MessageListItem(
                    text, Instant.now(), "Yadier Betancourt");
            newMessage.setUserColorIndex(3);
            List<MessageListItem> items = new ArrayList<>(list.getItems());
            items.add(newMessage);
            list.setItems(items);

            String answer = chatSession.multisentenceRespond(text);
            MessageListItem botMessage = new MessageListItem(
                    answer, Instant.now(), "Alice");
            botMessage.setUserColorIndex(1);
            List<MessageListItem> botItems = new ArrayList<>(list.getItems());
            botItems.add(botMessage);
            list.setItems(botItems);
        });
        input.setWidthFull();

        VerticalLayout chatContainer = new VerticalLayout();
        chatContainer.addClassNames(Flex.AUTO, Overflow.HIDDEN);

        chatContainer.add(list, input);
        add(chatContainer);
        setSizeFull();
        expand(list);

    }

}
