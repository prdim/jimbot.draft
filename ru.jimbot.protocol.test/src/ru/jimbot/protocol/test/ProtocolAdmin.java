/**
 * 
 */
package ru.jimbot.protocol.test;

import java.util.Iterator;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.jimbot.core.Message;
import ru.jimbot.http.admin.AbstractView;
import ru.jimbot.http.admin.ViewAddon;

/**
 * Панель веб-админки для управления протоколом: просмотр и отправка тестовых сообщений
 * @author spec
 *
 */
public class ProtocolAdmin extends AbstractView<VerticalLayout> implements ViewAddon {
	private Label logLabel = new Label("", Label.CONTENT_XHTML);
	private ProgressIndicator pi;
	private CheckBox cb;
	private TextField tIn;
	private TextField tOut;
	private TextField tMsg;
	private Worker worker;
	
	public ProtocolAdmin() {
		super(new VerticalLayout());
		getContent().setMargin(true);
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
					worker = new Worker();
					worker.start();
				} else {
					pi.setEnabled(false);
					worker.stop();
				}
			}
       
        });
        refreshState.addComponent(cb);
        getContent().addComponent(refreshState);
        tIn = new TextField();
        tIn.setInputPrompt("От кого");
        tOut = new TextField();
        tOut.setInputPrompt("Кому");
        tMsg = new TextField();
        tMsg.setInputPrompt("Сообщение");
        Button b = new Button("Отправить", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				TestProtocol.testSend((String)tIn.getValue(), (String)tOut.getValue(), (String)tMsg.getValue());
				tMsg.setValue("");
				update();
			}
		});
        getContent().addComponent(tIn);
        getContent().addComponent(tOut);
        getContent().addComponent(tMsg);
        getContent().addComponent(b);
        getContent().addComponent(logLabel);
	}
	
	private void update() {
		if(TestProtocol.getMessages().isEmpty()) {
			logLabel.setValue("");
			return;
		}
		StringBuffer sb = new StringBuffer();
		Iterator<Message> i = TestProtocol.getMessages().descendingIterator();
		while(i.hasNext()) {
			Message m = i.next();
			sb.append("<p>" + m.getSnIn() + "->" + m.getSnOut() + ": " + m.getMsg().replaceAll("\n", "<br/>") + "</p>");
		}
		logLabel.setValue(sb.toString());
	}

	@Override
	public void activated(Object... params) {
		update();
	}

	@Override
	public void deactivated(Object... params) {
		if(worker ==  null) return;
		worker.stop();
		pi.setEnabled(false);
		cb.setValue(false);
	}

	@Override
	public String getFragment() {
		return "addon-test-protocol";
	}

	@Override
	public String getName() {
		return "Отправка тестовых сообщений";
	}

	public class Worker extends Thread {

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
					update();
				}
			}
		}
		
	}
}
