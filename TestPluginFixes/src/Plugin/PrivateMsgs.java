package Plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
public class PrivateMsgs implements CommandExecutor{

public PrivateMsgs(Main plugin) {}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (args.length == 0) {
			p.sendMessage("�6[�4�ѧ6] �c������������� /pm <�����> <���������>");
			return true;
		}
		
		if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayerExact(args[0]))) {
			p.sendMessage("�6[�4�ѧ6] �c����� �� � ����");
			return true;
		}
		Player t = Bukkit.getPlayerExact(args[0]);
		if(p.getName() == t.getName()) {
			p.sendMessage("�6[�4�ѧ6] �c�� �� ������ ������ ������ ����!");
			return true;
		}
		if (args.length == 1) {
			p.sendMessage("�6[�4�ѧ6] �c������� ���� ���������");
			return true;
		}
			String message = "";
			for (int i = 1; i != args.length; i++) {
				message += args[i] +" ";
			}
			p.sendMessage("�6[�4�ѧ6] �8[�b�� �a>>> �b"+t.getName()+"�8]: �6"+ message);
			t.sendMessage("�6[�4�ѧ6] �8[�b"+p.getName()+" �a>>> �b �ۧ8]: �6"+ message);
			for(Player pls : Bukkit.getServer().getOnlinePlayers()) {
				if (pls.hasPermission("str.spy")) {
					pls.sendMessage("�7[SPY]: "+p.getName()+" >>> "+ t.getName()+": "+ message);
				}
			}
			
			return true;
	}

}
