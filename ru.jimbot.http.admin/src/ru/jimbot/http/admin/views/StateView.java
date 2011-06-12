/**
 * 
 */
package ru.jimbot.http.admin.views;

import ru.jimbot.core.MainProps;
import ru.jimbot.core.MsgStatCounter;
import ru.jimbot.core.services.UinConfig;
import ru.jimbot.http.admin.AbstractView;
import ru.jimbot.http.admin.internal.Activator;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

/**
 * @author spec
 *
 */
public class StateView extends AbstractView<VerticalLayout> {
	private Label statusLabel = new Label("", Label.CONTENT_XHTML);
	private ProgressIndicator pi;
	private Worker1 worker1;
	private String fragment;
	CheckBox cb;
	
	public StateView(String fragment) {
		super(new VerticalLayout());
		this.fragment = fragment;
		getContent().setMargin(true);
//		getContent().addComponent(statusLabel);
		HorizontalLayout refreshState = new HorizontalLayout();
		pi = new ProgressIndicator();
        pi.setIndeterminate(true);
        pi.setPollingInterval(1000);
        pi.setEnabled(false);
        refreshState.addComponent(pi);
        cb = new CheckBox("Автоматическое обновление");
        cb.setImmediate(true);
        cb.addListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if(event.getButton().booleanValue()) {
					pi.setEnabled(true);
					worker1 = new Worker1();
					worker1.start();
				} else {
					worker1.stop();
					pi.setEnabled(false);
				}
				
			}
        	
        });
        refreshState.addComponent(cb);
        getContent().addComponent(refreshState);
        getContent().addComponent(statusLabel);
        progress();
	}

	@Override
	public void activated(Object... params) {
		progress();
	}

	@Override
	public void deactivated(Object... params) {
		if(worker1 == null) return;
		worker1.stop();
		pi.setEnabled(false);
		cb.setValue(false);
	}

	/* (non-Javadoc)
	 * @see ru.jimbot.http.admin.View#getFragment()
	 */
	@Override
	public String getFragment() {
		return fragment;
	}

	public void progress() {
		StringBuffer sb = new StringBuffer();
		sb.append("<h1>Информация о работе бота</h1>");
		sb.append("<p>Память, использовано/доступно (Мб): " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1024/1024 
				+ "/" + Runtime.getRuntime().totalMemory()/1024/1024 + "</p>");
		sb.append("<h3>Сервисы бота</h3>");
		for(String i : MainProps.getInstance().getServiceNames()) {
			sb.append("<p><b>" + i + "</b> : " + (Activator.getExtendPointRegistry().getBotService(i).isRun() ? "[Запущен]" : "[Остановлен]"));
			sb.append("</p>");
			if(Activator.getExtendPointRegistry().getBotService(i).isRun()) {
				sb.append("<p>Очередь входящих сообщений: " + Activator.getExtendPointRegistry().getBotService(i).getInQueue().size() + "</p>");
				sb.append("<p>Статистика принятых сообщений по номерам");
				sb.append("<TABLE BORDER=\"1\"><TR><TD>UIN</TD><TD>1 минута</TD><TD>5 митут</TD><TD>60 минут</TD><TD>24 часа</TD><TD>Всего</TD></TR>");
				for(UinConfig u : Activator.getExtendPointRegistry().getBotService(i).getConfig().getUins()) {
					sb.append("<TR><TD>" + u.getProtocol() + ":" + u.getScreenName() + " ");
					sb.append(Activator.getExtendPointRegistry().getBotService(i).getProtocol(u.getScreenName()).isOnLine() ? "[ON]":"[OFF]");
					sb.append("</TD><TD>" + MsgStatCounter.getElement(u.getScreenName()).getMsgCount(MsgStatCounter.M1));
					sb.append("</TD><TD>" + MsgStatCounter.getElement(u.getScreenName()).getMsgCount(MsgStatCounter.M5));
					sb.append("</TD><TD>" + MsgStatCounter.getElement(u.getScreenName()).getMsgCount(MsgStatCounter.M60));
					sb.append("</TD><TD>" + MsgStatCounter.getElement(u.getScreenName()).getMsgCount(MsgStatCounter.H24));
					sb.append("</TD><TD>" + MsgStatCounter.getElement(u.getScreenName()).getMsgCount(MsgStatCounter.ALL));
					sb.append("</TD></TR>");
					if(!Activator.getExtendPointRegistry().getBotService(i).getProtocol(u.getScreenName()).isOnLine())
						sb.append(" " + Activator.getExtendPointRegistry().getBotService(i).getProtocol(u.getScreenName()).getLastError());
					
				}
				sb.append("</TABLE></p>");
			}
		}
		statusLabel.setValue(sb.toString());
	}
	
	public class Worker1 extends Thread {
		int current = 1;

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while (true) {
				try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (getApplication()) {
                	progress();
                }
			}
		}
		
	}
}
