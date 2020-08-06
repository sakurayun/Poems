package shrbox.github.poems;

import net.mamoe.mirai.console.command.BlockingCommand;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.JCommandManager;
import net.mamoe.mirai.console.plugins.Config;
import net.mamoe.mirai.console.plugins.PluginBase;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;

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
        JCommandManager.getInstance().register(this, new BlockingCommand("poreload", new ArrayList<>(), "重载Poems配置文件", "/poreload") {
            @Override
            public boolean onCommandBlocking(@NotNull CommandSender commandSender, @NotNull List<String> list) {
                loadCfg();
                commandSender.sendMessageBlocking("重载成功");
                return true;
            }
        });

        getEventListener().subscribeAlways(GroupMessageEvent.class, (GroupMessageEvent e) -> {
            if (e.getMessage().contentToString().equals("念诗")) {
                if (!config.getLongList("enableGroups").contains(e.getGroup().getId())) return;
                EventThread eventThread = new EventThread();
                eventThread.startThread(e);
            }
        });
    }
}