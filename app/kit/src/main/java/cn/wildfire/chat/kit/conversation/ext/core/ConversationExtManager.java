package cn.wildfire.chat.kit.conversation.ext.core;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import cn.wildfire.chat.kit.conversation.ext.FileExt;
import cn.wildfire.chat.kit.conversation.ext.ImageExt;
import cn.wildfire.chat.kit.conversation.ext.LocationExt;
import cn.wildfire.chat.kit.conversation.ext.RedExt;
import cn.wildfire.chat.kit.conversation.ext.TransinfoExt;
import cn.wildfirechat.model.Conversation;

public class ConversationExtManager {
    private static ConversationExtManager instance;
    private List<ConversationExt> conversationExts;

    private ConversationExtManager() {
        conversationExts = new ArrayList<>();
        init();
    }

    public static synchronized ConversationExtManager getInstance() {
        if (instance == null) {
            instance = new ConversationExtManager();
        }
        return instance;
    }

    private void init() {
        //registerExt(VoipExt.class);
        registerExt(ImageExt.class);
       // registerExt(ShootExt.class);
        registerExt(LocationExt.class);
        registerExt(RedExt.class);
        registerExt(TransinfoExt.class);
        registerExt(FileExt.class);
        //registerExt(CardExt.class);
        //registerExt(ExampleAudioInputExt.class);
    }

    public void registerExt(Class<? extends ConversationExt> clazz) {
        Constructor constructor;
        try {
            constructor = clazz.getConstructor();
            ConversationExt ext = (ConversationExt) constructor.newInstance();
            conversationExts.add(ext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unregisterExt(Class<? extends ConversationExt> clazz) {
        // TODO
    }

    public List<ConversationExt> getConversationExts(Conversation conversation) {
        List<ConversationExt> currentExts = new ArrayList<>();
        for (ConversationExt ext : this.conversationExts) {
            if (!ext.filter(conversation)) {
                currentExts.add(ext);
            }
        }
        return currentExts;
    }
}
