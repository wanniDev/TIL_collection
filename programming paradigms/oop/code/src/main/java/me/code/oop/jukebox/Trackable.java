package me.code.oop.jukebox;

/**
 * 음반 트랙 이동이 자유로운 제품의 경우 주입되는 인터페이스 ex) CD, MP3 등
 */
public interface Trackable {
	void nextSongBtn();
	void prevSongBtn();
}
