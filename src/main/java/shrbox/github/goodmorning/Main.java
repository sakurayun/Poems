package shrbox.github.goodmorning;

import net.mamoe.mirai.console.plugins.PluginBase;
import net.mamoe.mirai.message.GroupMessageEvent;

class Main extends PluginBase {
    public void onEnable() {
        getEventListener().subscribeAlways(GroupMessageEvent.class, (GroupMessageEvent e) -> {
            if (e.getMessage().contentToString().equals("早")) {
                EventThread eventThread = new EventThread();
                eventThread.startThread(e);
            }
        });
    }
}