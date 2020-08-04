package shrbox.github.goodmorning;

import com.google.gson.Gson;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EventThread extends Thread {
    GroupMessageEvent e;
    public void startThread(GroupMessageEvent event) {
        this.e = event;
        start();
    }

    @Override
    public void run() {
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH");
        int hour = Integer.parseInt(localTime.format(dateTimeFormatter));
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
        if (hour > 10 || hour < 6) {
            String message = "";
            switch (hour) {
                case 0 :
                case 1 :
                case 2 :
                case 3 :
                    message = "你就是熬夜冠军？";
                    break;
                case 4 :
                case 5 :
                    message = "这么早起？还是说你是个熬夜冠军？";
                    break;
                case 11 :
                    message = "都快中午了，还搁这早呢？";
                    break;
                case 12 :
                    message = "这都中午了，你还早啥啊";
                    break;
                case 13 :
                case 14 :
                case 15 :
                case 16 :
                case 17 :
                    message = "这都下午了，别早了";
                    break;
                case 18 :
                    message = "我服了，我都吃完晚饭了你还在这早？";
                    break;
                case 19 :
                case 20 :
                    message = "你是真的行，这都晚上了";
                    break;
                case 21 :
                case 22 :
                case 23:
                    message = "早啥呢，我都去睡觉了";
                    break;
            }
            e.getGroup().sendMessageAsync(MessageUtils.newChain(new At(e.getSender())).plus(message + "\n")
                    .plus(poem.text));
            return;
        }
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH点mm分ss秒");
        e.getGroup().sendMessageAsync(MessageUtils.newChain(new At(e.getSender()))
                .plus("早上好，现在是" + localDateTime.format(dateTimeFormatter1) + "\n")
                .plus(poem.text));
    }
}
