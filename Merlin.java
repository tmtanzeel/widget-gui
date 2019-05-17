public class Merlin {

	public String merlin(String zone) {
		String[] strArray = {"AKDT","CET", "CST", "CSET", "EAT", "GMT", "IST","PKT","4"};
		String[] fullArray = {"Alaska Daylight Time","Central Europian Time", "Central Standard Time", "Central Europian Summer Time", "East Africa Time", "Greenwich Mean Time", "Indian Standard Time","Pakistan Standard Time","Gulf Standard Time"};
		
		boolean found = false;
		int index = 0;
		String s = zone;
		for (int i = 0; i < strArray.length; i++) {
			if(s.equals(strArray[i])) {
				index = i; found = true; break;
			}
		}
		if(found)
			return (fullArray[index]);
		else
			return ("Never heard of this timezone");
		
	}

}