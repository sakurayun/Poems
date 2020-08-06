package shrbox.github.poems;

import com.google.gson.Gson;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageUtils;

public class EventThread extends Thread {
    GroupMessageEvent e;
    public void startThread(GroupMessageEvent event) {
        this.e = event;
        start();
    }

    @Override
    public void run() {
        String json = Connection.getURL("https://xiaojieapi.cn/API/Poems.php");
        if (json.equals("")) {
            e.getGroup().sendMessageAsync(MessageUtils.newChain(new At(e.getSender())).plus("接口错误"));
            return;
        }
        Gson gson = new Gson();
        Poem poem = gson.fromJson(json, Poem.class);
        if (poem.code != 1000) {
            e.getGroup().sendMessageAsync(MessageUtils.newChain(new At(e.getSender())).plus("接口错误"));
            return;
        }
        e.getGroup().sendMessageAsync(MessageUtils.newChain(new At(e.getSender()))
                .plus(poem.text));
    }
}
