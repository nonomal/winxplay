package xplay;

public class Xplay {

	public static void main(String[] args) {

		// xplayctl
		XplayCtl xc = new XplayCtl("192.168.1.16", 8700);

		Rect rect = new Rect(0, 0, 1920, 1080); // �ز���ʾ��λ����ߴ�
		
		// xc.playVideo(System.currentTimeMillis(), "PLAY_VIDEO_XXXX", 10, "/root/1.mp4", rect, "landscape", 0);
		// sleep 5s �ز���ͬ���л�(��Ƶ����Ƶ����Ƶ��ͼƬ��ͼƬ��ͼƬ��ͼƬ����Ƶ)ʱ����Ҫֹͣ��ֱ�ӷ�����һ���زĵĲ���ָ�xplay ���Զ��滻
		// xc.playVideo(System.currentTimeMillis(), "PLAY_VIDEO_XXXX", 10, "/root/2.mp4", rect, "landscape", 0);

		// xc.playImage(System.currentTimeMillis(), "PLAY_IMAGE_XXXX", 10, "/root/11.jpg", rect, "landscape", 0);

		Rect datetime = new Rect(0, 0, 500, 50);
		// xc.playDateTime(System.currentTimeMillis(), "PLAY_DATETIME_XXXX", 3, datetime, "landscape", 0, "", 30, "rgba(0, 128, 0, 100%)", "rgba(0, 0, 0, 50%)", "normal", "center");
		
		Rect qrcode = new Rect(0, 0, 100, 100);
		// xc.playQRCode(System.currentTimeMillis(), "PLAY_QRCODE_XXX", 5, "��ӭʹ��", qrcode, "landscape", 0);
		
		xc.playToast(System.currentTimeMillis(), "PLAY_TOAST_XXX", 2, "��װ�ɹ�", "landscape", 0, "success", 15);
		
		Rect scroll = new Rect(0, 0, -1, 50); // ������Ļֻ��֧�� ����ȫ�����Զ���� ���� ����ȫ�����Զ���� ...
		// xc.playScroll(System.currentTimeMillis(), "PLAY_SCROLL_XXX", 8, "1234567890", scroll, "landscape", 0, "", 30, "rgba(0, 128, 0, 100%)", "rgba(0, 0, 0, 50%)", "normal", "horizontal", 1);

		Rect text = new Rect(0, 0, 500, 50);
		// xc.playText(System.currentTimeMillis(), "PLAY_TEXT_XXX", 9, "55555555555", text, "landscape", 0, "", 30, "rgba(0, 128, 0, 100%)", "rgba(0, 0, 0, 50%)", "normal", "center");
		
		// xc.stopIndex(true, null);
		// xc.stopIndex(false, new ArrayList<String>(Arrays.asList("10")));
		// xc.moveIndex(10, 100, 100);
		
	}

}
