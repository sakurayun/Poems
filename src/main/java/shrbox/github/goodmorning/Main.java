package shrbox.github.goodmorning;

import net.mamoe.mirai.console.plugins.Config;
import net.mamoe.mirai.console.plugins.PluginBase;
import net.mamoe.mirai.message.GroupMessageEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Main extends PluginBase {
    public Config config;
    public void loadCfg() {
        config = loadConfig("config.yml");
        List<Long> temp_list = new ArrayList<>();
        Collections.addAll(temp_list, 1145141919L, 1919810L);
        config.setIfAbsent("enableGroups", temp_list);
        config.save();
        temp_list.clear();
    }

    public void onEnable() {
        loadCfg();
        getEventListener().subscribeAlways(GroupMessageEvent.class, (GroupMessageEvent e) -> {
            if (e.getMessage().contentToString().equals("早")) {
                if (!config.getLongList("enableGroups").contains(e.getGroup().getId())) return;
                EventThread eventThread = new EventThread();
                eventThread.startThread(e);
            }
        });
    }
}