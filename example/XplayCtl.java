package xplay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

class Rect {

	Rect(int _x, int _y, int _width, int _height) {
		x = _x;
		y = _y;
		width = _width;
		height = _height;
	}

	int x;
	int y;
	int width;
	int height;
}

class Dep {
	String path; // �ز�·��
	String type; // �ز�����(video or pic)
	int duration; // �زĳ���ʱ��(����Ƶ)
}

public class XplayCtl {

	XplayCtl(String _host, int _port) {
		host = _host;
		port = _port;
	}

	private String host;
	private int port;
	private Socket socket = null;

	private boolean connXplay(String _data) {
		try {
			if (socket == null || !socket.isConnected())
				socket = new Socket(host, port);
			OutputStreamWriter os = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
			os.write(_data + "\n#End\n");
			os.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			String data = null;
			while ((data = br.readLine()) != null) {
				if (data.contains("#End"))
					break;
				System.out.println(data);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean playXplay(Map<String, Object> _data) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(_data);
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(json);
		String prettyJsonString = gson.toJson(je);
		System.out.print(prettyJsonString);
		return this.connXplay(prettyJsonString);
	}

	/*
	 * ����(��Ƶ��ͼƬ) 
	 * _content һ���زģ�ÿһ���ز�Ϊһ�� Dep �����ز�·��������(libName)������ʱ��(����Ƶ)
	 */
	public boolean playSequence(long _start, String _id, int _index, ArrayList<Dep> _content, Rect _rect, String _mode,
			int _rotate) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("id", _id);
		data.put("type", "sequence");
		data.put("start", _start);
		data.put("libName", "video");
		params.put("zIndex", _index);
		params.put("left", _rect.x);
		params.put("top", _rect.y);
		params.put("width", _rect.width);
		params.put("height", _rect.height);
		params.put("screen_mode", _mode);
		params.put("screen_rotate", _rotate);
		data.put("params", params);
		data.put("deps", _content);
		return this.playXplay(data);
	}

	/*
	 * ��Ƶ����ý��� ... 
	 * _start ���زĿ�ʼ���ŵ�ʱ�侫ȷ������ (��ƵԤ��������:
	 * ��ǰ500ms-1000ms����ָ��xplay����Ԥ������Ƶ)(Ҳ����_startʱ����12:00:00����11:59:59��ָ���xplay) 
	 * _id ���ز�Ψһ��ʶ��(����ʹ��) 
	 * _index ���ز���ʹ�õĲ�(0-999)(����ԽСԽ��ǰ��һ��1-9����Ϊ�����㣬��ʾһЩ��ʾ����LOGO��֪ͨ����Ϣʹ�� ...) 
	 * _content �زĴ洢·��
	 * _rect �ز���ʾλ����ߴ�(�زĿ��������������죬����������ʾ) 
	 * _mode ��Ļģʽ(������landscape��������portrait)
	 * _rotate ��ת�Ƕ�(������0��180��������90��270)
	 */
	public boolean playVideo(long _start, String _id, int _index, String _content, Rect _rect, String _mode,
			int _rotate) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("id", _id);
		data.put("type", "play");
		data.put("start", _start);
		data.put("libName", "video");
		params.put("zIndex", _index);
		params.put("path", _content);
		params.put("left", _rect.x);
		params.put("top", _rect.y);
		params.put("width", _rect.width);
		params.put("height", _rect.height);
		params.put("screen_mode", _mode);
		params.put("screen_rotate", _rotate);
		data.put("params", params);
		return this.playXplay(data);
	}

	/*
	 * ͼƬ������ʹ������Ƶ��ͬ
	 */
	public boolean playImage(long _start, String _id, int _index, String _content, Rect _rect, String _mode,
			int _rotate) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("id", _id);
		data.put("type", "play");
		data.put("start", _start);
		data.put("libName", "pic");
		params.put("zIndex", _index);
		params.put("path", _content);
		params.put("left", _rect.x);
		params.put("top", _rect.y);
		params.put("width", _rect.width);
		params.put("height", _rect.height);
		params.put("screen_mode", _mode);
		params.put("screen_rotate", _rotate);
		data.put("params", params);
		return this.playXplay(data);
	}

	/*
	 * ��ά�� _content ����Ϊ��ά���е�����
	 */
	public boolean playQRCode(long _start, String _id, int _index, String _content, Rect _rect, String _mode,
			int _rotate) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("id", _id);
		data.put("type", "play");
		data.put("start", _start);
		data.put("libName", "qrcode");
		params.put("zIndex", _index);
		params.put("content", _content);
		params.put("left", _rect.x);
		params.put("top", _rect.y);
		params.put("width", _rect.width);
		params.put("height", _rect.height);
		params.put("screen_mode", _mode);
		params.put("screen_rotate", _rotate);
		data.put("params", params);
		return this.playXplay(data);
	}

	/*
	 * GIF������ʹ������Ƶ��ͬ
	 */
	public boolean playGIF(long _start, String _id, int _index, String _content, Rect _rect, String _mode,
			int _rotate) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("id", _id);
		data.put("type", "play");
		data.put("start", _start);
		data.put("libName", "gif");
		params.put("zIndex", _index);
		params.put("path", _content);
		params.put("left", _rect.x);
		params.put("top", _rect.y);
		params.put("width", _rect.width);
		params.put("height", _rect.height);
		params.put("screen_mode", _mode);
		params.put("screen_rotate", _rotate);
		data.put("params", params);
		return this.playXplay(data);
	}

	/*
	 * ��Ϣ��ʾ�� _content ��ʾ������ 
	 * _toast_type ��ʾ������(notice��success��warning��error)
	 * _duration ��ʾ����ʾʱ��(���Ϊ0��������ʾ)
	 */
	public boolean playToast(long _start, String _id, int _index, String _content, String _mode, int _rotate,
			String _toast_type, int _duration) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("id", _id);
		data.put("type", "play");
		data.put("start", _start);
		data.put("libName", "toast");
		params.put("zIndex", _index);
		params.put("screen_mode", _mode);
		params.put("screen_rotate", _rotate);
		params.put("content", _content);
		params.put("toast_type", _toast_type);
		params.put("duration", _duration);
		data.put("params", params);
		return this.playXplay(data);
	}

	/*
	 * ����ͷ
	 *  _camera_width �� _camera_height Ϊ����ͷ�ɼ�����ֱ��ʣ�����(1280x720)
	 */
	public boolean playCamera(long _start, String _id, int _index, String _content, Rect _rect, String _mode,
			int _rotate, int _camera_width, int _camera_height) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("id", _id);
		data.put("type", "play");
		data.put("start", _start);
		data.put("libName", "camera");
		params.put("zIndex", _index);
		params.put("device", _content);
		params.put("camera_width", _camera_width);
		params.put("camera_height", _camera_height);
		params.put("left", _rect.x);
		params.put("top", _rect.y);
		params.put("width", _rect.width);
		params.put("height", _rect.height);
		params.put("screen_mode", _mode);
		params.put("screen_rotate", _rotate);
		data.put("params", params);
		return this.playXplay(data);
	}

	/*
	 * �ı� 
	 * _color "rgba(0, 128, 0, 100%)" ָ���ı���ɫRGB��͸����A 
	 * _bgcolor rgba(0, 0, 0, 0%) ָ��������ɫRGB��͸���� 
	 * _style �ı���ʽ��֧�� normal��bold��italic��underline��strikethrough
	 * _align ���뷽ʽ��֧�� center��right��left _ptsize �����С _ttf ָ�������ļ�������ʹ��Ĭ������
	 */
	public boolean playText(long _start, String _id, int _index, String _content, Rect _rect, String _mode, int _rotate,
			String _ttf, int _ptsize, String _color, String _bgcolor, String _style, String _align) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("id", _id);
		data.put("type", "play");
		data.put("start", _start);
		data.put("libName", "text");
		params.put("zIndex", _index);
		params.put("left", _rect.x);
		params.put("top", _rect.y);
		params.put("width", _rect.width);
		params.put("height", _rect.height);
		params.put("screen_mode", _mode);
		params.put("screen_rotate", _rotate);
		params.put("content", _content);
		params.put("color", _color);
		params.put("bgcolor", _bgcolor);
		params.put("font_size", _ptsize);
		params.put("align", _align);
		params.put("style", _style);
		data.put("params", params);
		return this.playXplay(data);
	}

	/*
	 * ������Ļ ������Ļֻ��֧�� ����ȫ�����Զ���� ���� ����ȫ�����Զ���� ... 
	 * _orientation �ƶ����� horizontal��vertical 
	 * _speed �ƶ��ٶ� Ĭ��Ϊ 1, Ҳ����ÿ֡�ƶ�һ������ ����� FPS30 Ҳ���� _speed = 1 ÿ�� 30������
	 */
	public boolean playScroll(long _start, String _id, int _index, String _content, Rect _rect, String _mode,
			int _rotate, String _ttf, int _ptsize, String _color, String _bgcolor, String _style, String _orientation,
			int _speed) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("id", _id);
		data.put("type", "play");
		data.put("start", _start);
		data.put("libName", "scroll");
		params.put("zIndex", _index);
		params.put("left", _rect.x);
		params.put("top", _rect.y);
		params.put("width", _rect.width);
		params.put("height", _rect.height);
		params.put("screen_mode", _mode);
		params.put("screen_rotate", _rotate);
		params.put("content", _content);
		params.put("color", _color);
		params.put("bgcolor", _bgcolor);
		params.put("font_size", _ptsize);
		params.put("style", _style);
		params.put("orientation", _orientation);
		params.put("speed", _speed);
		data.put("params", params);
		return this.playXplay(data);
	}

	/*
	 * ����ʱ��
	 */
	public boolean playDateTime(long _start, String _id, int _index, Rect _rect, String _mode, int _rotate, String _ttf,
			int _ptsize, String _color, String _bgcolor, String _style, String _align) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("id", _id);
		data.put("type", "play");
		data.put("start", _start);
		data.put("libName", "datetime");
		params.put("zIndex", _index);
		params.put("left", _rect.x);
		params.put("top", _rect.y);
		params.put("width", _rect.width);
		params.put("height", _rect.height);
		params.put("screen_mode", _mode);
		params.put("screen_rotate", _rotate);
		params.put("color", _color);
		params.put("bgcolor", _bgcolor);
		params.put("font_size", _ptsize);
		params.put("align", _align);
		params.put("style", _style);
		params.put("font_ttf", _ttf);
		data.put("params", params);
		return this.playXplay(data);
	}

	/*
	 * �ƶ���ǰ������ ...
	 */
	public boolean moveIndex(int _index, int _x, int _y) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("type", "move");
		params.put("zIndex", _index);
		params.put("left", _x);
		params.put("top", _y);
		data.put("params", params);
		return this.playXplay(data);
	}

	/*
	 * ֹͣȫ�������ָ���� ...
	 */
	public boolean stopIndex(boolean _all, ArrayList<String> _indexs) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("type", "stop");
		if (_all) {
			params.put("all", true);
		} else {
			params.put("ids", _indexs);
		}
		data.put("params", params);
		return this.playXplay(data);
	}

	/*
	 * ���� 
	 * _path �����ļ������浽��·��(����:/dev/shm/snap.jpg)
	 */
	public boolean snapXplay(String _path) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("type", "snap");
		params.put("path", _path);
		data.put("params", params);
		return this.playXplay(data);
	}

}
