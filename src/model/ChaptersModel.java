package model;

import java.util.ArrayList;
import java.util.Observable;

public class ChaptersModel extends Observable{
	private ArrayList<ChapterBO> lChapters = new ArrayList<ChapterBO>();

	public void add(ChapterBO chapter){
		lChapters.add(chapter);
		setChanged();
		notifyObservers();
	}
	
	public ChapterBO get(int index) {
		setChanged();
		notifyObservers();
		return this.lChapters.get(index);
	}
	
	public ArrayList<ChapterBO> getlChapters() {
		return lChapters;
	}
	
	public void setlChapters(ArrayList<ChapterBO> lChapters) {
		this.lChapters = lChapters;
	}

}
