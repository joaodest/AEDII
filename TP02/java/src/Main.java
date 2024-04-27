
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		Data data = new Data();

		String caminhoVerde = "/tmp/characters.csv";
		String caminhoPc = "C:/Users/joaod/Downloads/characters.csv";
		data.lerCsv(caminhoVerde);

		// Q01.questao1(data, sc);
		// Q03.questao3(data, sc);
		// Q05.questao05(data, sc);
		// Q07.questao07(data, sc);
		// Q09.questao09(data, sc);
		// Q11.questao11(data, sc);
		// Q13.questao13(data, sc);
		// Q15.questao15(data, sc);
		Q16.questao16(data, sc);
		sc.close();
	}

	public static boolean isFim(String s) {
		return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
	}

}

class Q01 {

	public static void questao1(Data data, Scanner sc) {
		while (sc.hasNext()) {
			String input = sc.next();

			if (Main.isFim(input))
				break;

			Personagem personagem = data.getPersonagemById(input);

			if (!personagem.equals(null)) {
				System.out.println(personagem);
			}
		}
	}
}

class Q03 {

	public static void questao3(Data data, Scanner sc) {
		ArrayList<Personagem> personagens = new ArrayList<>();
		Personagem personagem = new Personagem();

		while (sc.hasNextLine()) {
			String input = sc.nextLine();

			if (Main.isFim(input))
				break;

			personagem = data.getPersonagemById(input);
			personagens.add(personagem);

		}

		while (sc.hasNextLine()) {
			String name = sc.nextLine();

			if (Main.isFim(name))
				break;

			isPersonagemOnList(name, personagens);
		}
	}

	private static void isPersonagemOnList(String name, ArrayList<Personagem> personagens) {
		boolean found = false;
		for (Personagem p : personagens) {
			if (p.getPersonagemName().equals(name)) {
				System.out.println("SIM");
				found = true;
				break;
			}
		}
		if (!found) {
			System.out.println("NAO");
		}
	}

}

class Q05 {
	public static void questao05(Data data, Scanner sc) throws IOException {
		ArrayList<Personagem> personagens = new ArrayList<>();
		Personagem personagem = new Personagem();

		while (sc.hasNextLine()) {
			String input = sc.nextLine();

			if (Main.isFim(input))
				break;

			personagem = data.getPersonagemById(input);
			personagens.add(personagem);

		}

		long startTime = System.nanoTime();
		int[] stats = new int[2];
		selectionSort(personagens, stats);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;

		FileWriter writer = new FileWriter("829255_selection.txt");
		writer.write("829255\t" + stats[0] + "\t" + stats[1] + "\t" + duration + "\n");
		writer.close();

		Data.printSortedList(personagens);

	}

	private static void selectionSort(ArrayList<Personagem> personagens, int[] stats) {
		int length = personagens.size();
		for (int i = 0; i < length; i++) {
			int menor = i;
			for (int j = i + 1; j < length; j++) {
				stats[0]++;
				if (Data.compareStrings(personagens.get(j).getPersonagemName(),
						personagens.get(menor).getPersonagemName()) < 0) {
					menor = j;
				}
			}
			if (menor != i) {
				swap(personagens, i, menor);
				stats[1] += 3;
			}
		}
	}

	public static void swap(ArrayList<Personagem> list, int index1, int index2) {
		Personagem temp = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, temp);
	}
}

class Q07 {
	public static void questao07(Data data, Scanner sc) throws IOException {
		ArrayList<Personagem> personagens = new ArrayList<>();
		Personagem personagem = new Personagem();

		while (sc.hasNextLine()) {
			String input = sc.nextLine();

			if (Main.isFim(input))
				break;

			personagem = data.getPersonagemById(input);
			personagens.add(personagem);

		}
		long startTime = System.nanoTime();
		int[] stats = new int[2];
		insertionSort(personagens, stats);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;

		FileWriter writer = new FileWriter("829255_insertion.txt");
		writer.write("829255\t" + stats[0] + "\t" + stats[1] + "\t" + duration + "\n");
		writer.close();

		Data.printSortedList(personagens);

	}

	private static void insertionSort(ArrayList<Personagem> personagens, int[] stats) {
		int length = personagens.size();
		for (int i = 1; i < length; i++) {
			Personagem current = personagens.get(i);
			int j = i - 1;

			while (j >= 0 && (compareDates(personagens.get(j).getPersonagemDate(), current.getPersonagemDate()) > 0
					|| (compareDates(personagens.get(j).getPersonagemDate(), current.getPersonagemDate()) == 0
							&& Data.compareStrings(personagens.get(j).getPersonagemName(),
									current.getPersonagemName()) > 0))) {
				personagens.set(j + 1, personagens.get(j));
				j--;
				stats[1]++;
			}
			personagens.set(j + 1, current);
		}
	}

	private static int compareDates(String date1, String date2) {

		String[] parts1 = Data.splitString(date1, '-');
		String[] parts2 = Data.splitString(date2, '-');

		int yearComparison = Data.compareStrings(parts1[2], parts2[2]);
		if (yearComparison != 0)
			return yearComparison;

		int monthComparison = Data.compareStrings(parts1[1], parts2[1]);
		if (monthComparison != 0)
			return monthComparison;

		return parts1[0].compareTo(parts2[0]);
	}
}

class Q09 {
	public static void questao09(Data data, Scanner sc) throws IOException {
		ArrayList<Personagem> personagens = new ArrayList<>();
		Personagem personagem;
		while (sc.hasNextLine()) {
			String input = sc.nextLine();

			if (Main.isFim(input))
				break;

			personagem = data.getPersonagemById(input);
			personagens.add(personagem);
		}

		long startTime = System.nanoTime();
		int[] stats = new int[2];
		heapSort(personagens, stats);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;

		FileWriter writer = new FileWriter("829255_heapsort.txt");
		writer.write("829255\t" + stats[0] + "\t" + stats[1] + "\t" + duration + "\n");
		writer.close();

		Data.printSortedList(personagens);
	}

	private static void heapSort(ArrayList<Personagem> personagens, int[] stats) {
		int length = personagens.size();

		for (int i = length / 2 - 1; i >= 0; i--) {
			heapify(personagens, length, i, stats);
		}

		for (int i = length - 1; i >= 0; i--) {
			Personagem temp = personagens.get(0);
			personagens.set(0, personagens.get(i));
			personagens.set(i, temp);
			stats[1] += 3;

			heapify(personagens, i, 0, stats);
		}
	}

	private static void heapify(ArrayList<Personagem> personagens, int n, int i, int[] stats) {
		int largest = i;
		int left = 2 * i + 1;
		int right = 2 * i + 2;

		if (left < n) {
			stats[0]++;
			int compareResult = Data.compareStrings(personagens.get(left).getHairColour(),
					personagens.get(largest).getHairColour());
			if (compareResult > 0
					|| (compareResult == 0 && Data.compareStrings(personagens.get(left).getPersonagemName(),
							personagens.get(largest).getPersonagemName()) > 0)) {
				largest = left;
			}
		}

		if (right < n) {
			stats[0]++;
			int compareResult = Data.compareStrings(personagens.get(right).getHairColour(),
					personagens.get(largest).getHairColour());
			if (compareResult > 0
					|| (compareResult == 0 && Data.compareStrings(personagens.get(right).getPersonagemName(),
							personagens.get(largest).getPersonagemName()) > 0)) {
				largest = right;
			}
		}

		if (largest != i) {
			Personagem swap = personagens.get(i);
			personagens.set(i, personagens.get(largest));
			personagens.set(largest, swap);
			stats[1] += 3;

			heapify(personagens, n, largest, stats);
		}
	}
}

class Q11 {
	public static void questao11(Data data, Scanner sc) throws IOException {
		ArrayList<Personagem> personagens = new ArrayList<>();
		Personagem personagem;
		while (sc.hasNextLine()) {
			String input = sc.nextLine();

			if (Main.isFim(input))
				break;

			personagem = data.getPersonagemById(input);
			personagens.add(personagem);
		}

		long startTime = System.nanoTime();
		int[] stats = new int[2];
		countingSort(personagens, stats);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;

		FileWriter writer = new FileWriter("829255_countingsort.txt");
		writer.write("829255\t" + stats[0] + "\t" + stats[1] + "\t" + duration + "\n");
		writer.close();

		Data.printSortedList(personagens);
	}

	private static void countingSort(ArrayList<Personagem> personagens, int[] stats) {
		int minYear = Integer.MAX_VALUE;
		int maxYear = Integer.MIN_VALUE;
		for (Personagem p : personagens) {
			if (p.getYearOfBirth() < minYear) {
				minYear = p.getYearOfBirth();
			}
			if (p.getYearOfBirth() > maxYear) {
				maxYear = p.getYearOfBirth();
			}
		}

		int range = maxYear - minYear + 1;
		@SuppressWarnings("unchecked")
		ArrayList<Personagem>[] count = new ArrayList[range];
		for (int i = 0; i < range; i++) {
			count[i] = new ArrayList<>();
		}

		for (Personagem p : personagens) {
			int index = p.getYearOfBirth() - minYear;
			count[index].add(p);
			stats[1]++;
		}

		for (ArrayList<Personagem> bucket : count) {
			if (bucket.size() > 1) {
				bucket.sort((p1, p2) -> {
					int nameComparison = p1.getPersonagemName().compareTo(p2.getPersonagemName());
					return nameComparison;
				});
				stats[1] += bucket.size();
			}
		}

		int index = 0;
		for (ArrayList<Personagem> bucket : count) {
			for (Personagem p : bucket) {
				personagens.set(index++, p);
			}
		}
	}
}

class Q13 {
	public static void questao13(Data data, Scanner sc) throws IOException {
		ArrayList<Personagem> personagens = new ArrayList<>();
		Personagem personagem;
		while (sc.hasNextLine()) {
			String input = sc.nextLine();

			if (Main.isFim(input))
				break;

			personagem = data.getPersonagemById(input);
			personagens.add(personagem);
		}

		long startTime = System.nanoTime();
		int[] stats = new int[2];
		mergeSort(personagens, 0, personagens.size() - 1, stats);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;

		Data.printSortedList(personagens);

		FileWriter writer = new FileWriter("829255_mergesort.txt");
		writer.write("829255\t" + stats[0] + "\t" + stats[1] + "\t" + duration + "\n");
		writer.close();
	}

	private static void mergeSort(ArrayList<Personagem> personagens, int esq, int dir, int[] stats) {
		if (esq < dir) {
			int mid = (esq + dir) / 2;
			mergeSort(personagens, esq, mid, stats);
			mergeSort(personagens, mid + 1, dir, stats);
			merge(personagens, esq, mid, dir, stats);
		}
	}

	private static void merge(ArrayList<Personagem> personagens, int esq, int mid, int dir, int[] stats) {
		ArrayList<Personagem> left = new ArrayList<>(mid - esq + 1);
		ArrayList<Personagem> right = new ArrayList<>(dir - mid);

		for (int i = 0; i <= mid - esq; i++) {
			left.add(personagens.get(esq + i));
		}
		for (int j = 0; j < dir - mid; j++) {
			right.add(personagens.get(mid + 1 + j));
		}

		int i = 0, j = 0;
		int k = esq;

		while (i < left.size() && j < right.size()) {
			stats[0]++;
			Personagem leftPerson = left.get(i);
			Personagem rightPerson = right.get(j);
			boolean leftEmpty = leftPerson.getActorName().isEmpty();
			boolean rightEmpty = rightPerson.getActorName().isEmpty();

			if (leftEmpty && !rightEmpty) {
				personagens.set(k, leftPerson);
				i++;
			} else if (!leftEmpty && rightEmpty) {
				personagens.set(k, rightPerson);
				j++;
			} else {
				int compResult = Data.compareStrings(leftPerson.getActorName(), rightPerson.getActorName());
				if (compResult < 0 || (compResult == 0
						&& Data.compareStrings(leftPerson.getActorName(), rightPerson.getActorName()) <= 0)) {
					personagens.set(k, leftPerson);
					i++;
				} else {
					personagens.set(k, rightPerson);
					j++;
				}
			}
			stats[1]++;
			k++;
		}

		while (i < left.size()) {
			personagens.set(k, left.get(i));
			i++;
			k++;
			stats[1]++;
		}

		while (j < right.size()) {
			personagens.set(k, right.get(j));
			j++;
			k++;
			stats[1]++;
		}
	}
}

class Q15 {
	public static void questao15(Data data, Scanner sc) throws IOException {
		ArrayList<Personagem> personagens = new ArrayList<>();
		Personagem personagem;

		while (sc.hasNextLine()) {
			String input = sc.nextLine();

			if (Main.isFim(input))
				break;

			personagem = data.getPersonagemById(input);
			personagens.add(personagem);
		}

		long startTime = System.nanoTime();
		int[] stats = new int[2];
		selectionSortParcial(personagens, stats);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;

		FileWriter writer = new FileWriter("829255_selectionsort_parcial.txt");
		writer.write("829255\t" + stats[0] + "\t" + stats[1] + "\t" + duration + "\n");
		writer.close();

		printPartialSortedList(personagens, 10);
	}

	private static void selectionSortParcial(ArrayList<Personagem> personagens, int[] stats) {
		int length = personagens.size();

		for (int i = 0; i < 10; i++) {
			int menor = i;
			for (int j = i + 1; j < length; j++) {
				stats[0]++;
				if (Data.compareStrings(personagens.get(j).getPersonagemName(),
						personagens.get(menor).getPersonagemName()) < 0) {
					menor = j;
				}
			}
			if (menor != i) {
				swap(personagens, i, menor);
				stats[1] += 3;
			}
		}
	}

	public static void swap(ArrayList<Personagem> list, int index1, int index2) {
		Personagem temp = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, temp);
	}

	private static void printPartialSortedList(ArrayList<Personagem> personagens, int count) {
		for (int i = 0; i < count; i++) {
			System.out.println(personagens.get(i));
		}
	}
}

class Q16 {
	public static void questao16(Data data, Scanner sc) throws IOException {
		ArrayList<Personagem> personagens = new ArrayList<>();
		Personagem personagem;

		while (sc.hasNextLine()) {
			String input = sc.nextLine();

			if (Main.isFim(input))
				break;

			personagem = data.getPersonagemById(input);
			personagens.add(personagem);
		}

		long startTime = System.nanoTime();
		int[] stats = new int[2];
		quickSortParcial(personagens, stats, 0, personagens.size() - 1, 10);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;

		FileWriter writer = new FileWriter("829255_quicksort_parcial.txt");
		writer.write("829255\t" + stats[0] + "\t" + stats[1] + "\t" + duration + "\n");
		writer.close();

		printSortedPartialList(personagens.subList(0, Math.min(10, personagens.size())));
	}

	private static void printSortedPartialList(List<Personagem> personagens) {
	    int limit = Math.min(10, personagens.size());
	    for (int i = 0; i < limit; i++) {
	        System.out.println(personagens.get(i));
	    }
	}

	private static void quickSortParcial(ArrayList<Personagem> personagens, int[] stats, int esq, int dir, int limit) {
		if (esq < dir && esq < limit) {
			int pivot = partition(personagens, stats, esq, dir);
			quickSortParcial(personagens, stats, esq, pivot - 1, limit);
			quickSortParcial(personagens, stats, pivot + 1, dir, limit);
		}
	}

	private static int partition(ArrayList<Personagem> personagens, int[] stats, int esq, int dir) {
		Personagem pivot = personagens.get(dir);
		int i = (esq - 1);

		for (int j = esq; j < dir; j++) {
			stats[0]++;
			int compareResult = Data.compareStrings(personagens.get(j).getHouse(), pivot.getHouse());
			if (compareResult < 0 || (compareResult == 0
					&& Data.compareStrings(personagens.get(j).getActorName(), pivot.getActorName()) <= 0)) {
				i++;
				swap(personagens, i, j);
				stats[1] += 3;
			}
		}

		swap(personagens, i + 1, dir);
		stats[1] += 3;

		return i + 1;
	}

	public static void swap(ArrayList<Personagem> list, int index1, int index2) {
		Personagem temp = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, temp);
	}
}

class Data {
	private List<Personagem> personagens = new ArrayList<>();
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	public Data() {
		super();
		this.personagens = new ArrayList<>();
	}

	public List<Personagem> getPersonagens() {
		return personagens;
	}

	public Personagem getPersonagemById(String idString) {
		UUID id = UUID.fromString(idString);

		for (Personagem p : personagens) {
			if (p.getId().equals(id)) {
				return p;
			}
		}
		return null;
	}

	public void lerCsv(String path) {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String linha;

			br.readLine();
			while ((linha = br.readLine()) != null) {

				try {
					Personagem p = instanciaPersonagemPeloCsv(linha);
					personagens.add(p);
				} catch (Exception e) {
					System.err.println("Erro ao processar a linha: " + linha);
					e.printStackTrace();
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Personagem instanciaPersonagemPeloCsv(String linha) throws ParseException {
		String[] values = splitString(linha, ';');
		UUID id = UUID.fromString(values[0]);
		String name = values[1];

		String alternateNamesRaw = replaceAllSimple(values[2], "[", "");
		alternateNamesRaw = replaceAllSimple(alternateNamesRaw, "]", "");
		alternateNamesRaw = replaceAllSimple(alternateNamesRaw, "\"", "");

		ArrayList<String> alternateNames = new ArrayList<>(Arrays.asList(splitString(alternateNamesRaw, ',')));

		String house = values[3];
		String ancestry = values[4];
		String species = values[5];
		String patronus = values[6];
		boolean hogwartsStaff = values[7].equals("VERDADEIRO");
		String hogwartsStudent = values[8];
		String actorName = values[9];
		boolean alive = values[10].equalsIgnoreCase("VERDADEIRO");
		Date dateOfBirth = values[12].isEmpty() ? null : sdf.parse(values[12]);
		int yearOfBirth = Integer.parseInt(values[13]);
		String eyeColour = values[14];
		String gender = values[15];
		String hairColour = values[16];
		boolean wizard = values[17].equalsIgnoreCase("VERDADEIRO");

		return new Personagem(id.toString(), name, alternateNames, house, ancestry, species, patronus, hogwartsStaff,
				hogwartsStudent, actorName, alive, dateOfBirth, yearOfBirth, eyeColour, gender, hairColour, wizard);
	}

	public static String[] splitString(String input, char delimiter) {
		List<String> result = new ArrayList<>();
		StringBuilder buffer = new StringBuilder();

		for (char ch : input.toCharArray()) {
			if (ch == delimiter) {
				result.add(buffer.toString());
				buffer = new StringBuilder();
			} else {
				buffer.append(ch);
			}
		}
		if (buffer.length() > 0) {
			result.add(buffer.toString());
		}
		return result.toArray(new String[0]);
	}

	public static String replaceAllSimple(String input, String toReplace, String replacement) {
		StringBuilder result = new StringBuilder();
		int start = 0;
		int end = input.indexOf(toReplace);
		while (end != -1) {
			result.append(input, start, end).append(replacement);
			start = end + toReplace.length();
			end = input.indexOf(toReplace, start);
		}
		result.append(input.substring(start));
		return result.toString();
	}

	public static int compareStrings(String s1, String s2) {
		int length1 = s1.length();
		int length2 = s2.length();
		int minLen = Math.min(length1, length2);

		for (int i = 0; i < minLen; i++) {
			char c1 = s1.charAt(i);
			char c2 = s2.charAt(i);

			if (c1 != c2) {
				return c1 - c2;
			}
		}

		return length1 - length2;
	}

	public static void printSortedList(ArrayList<Personagem> personagens) {
		for (Personagem p : personagens) {
			System.out.println(p);
		}

	}
}

class Personagem {

	private UUID _id;
	private int _yearOfBirth;
	private String _name;
	private ArrayList<String> _alternateNames;
	private String _house;
	private String _ancestry;
	private String _species;
	private String _patronus;
	private String _hogwartsStudent;
	private String _actorName;
	private String _eyeColour;
	private String _gender;
	private String _hairColour;
	private Date _dateOfBirth;
	private boolean _hogwartsStaff;
	private boolean _alive;
	private boolean _wizard;

	public Personagem() {
	}

	public Personagem(String id, String name, ArrayList<String> alternateNames, String house, String ancestry,
			String species, String patronus, boolean hogwartsStaff, String hogwartsStudent, String actorName,
			boolean alive, Date dateOfBirth, int yearOfBirth, String eyeColour, String gender, String hairColour,
			boolean wizard) {

		_id = UUID.fromString(id);
		_yearOfBirth = yearOfBirth;
		_name = name;
		_alternateNames = alternateNames;
		_house = house;
		_ancestry = ancestry;
		_species = species;
		_patronus = patronus;
		_hogwartsStudent = hogwartsStudent;
		_actorName = actorName;
		_eyeColour = eyeColour;
		_gender = gender;
		_hairColour = hairColour;
		_dateOfBirth = dateOfBirth;
		_hogwartsStaff = hogwartsStaff;
		_alive = alive;
		_wizard = wizard;
	}

	public static Personagem getPersonagem() {
		return new Personagem();
	}

	public UUID getId() {
		return _id;
	}

	public String getPersonagemName() {
		return _name;
	}

	public String getPersonagemDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(_dateOfBirth);
	}

	public String getHairColour() {
		return _hairColour;
	}

	public int getYearOfBirth() {
		return _yearOfBirth;
	}

	public String getActorName() {
		return _actorName;
	}

	public String getHouse() {
		return _house;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDate = (_dateOfBirth != null) ? sdf.format(_dateOfBirth) : "";

		String alternateNamesFormatted = Data.replaceAllSimple(
				Data.replaceAllSimple(Data.replaceAllSimple(this._alternateNames.toString(), "[", ""), "]", ""), "'",
				"");

		return String.format(
				"[%s ## %s ## {%s} ## %s ## %s ## %s ## %s ## %b ## %b ## %s ## %b ## %s ## %d ## %s ## %s ## %s ## %b]",
				this._id, this._name, alternateNamesFormatted, this._house, this._ancestry, this._species,
				this._patronus, this._hogwartsStaff, this._hogwartsStudent.equalsIgnoreCase("VERDADEIRO"),
				this._actorName, this._alive, formattedDate, this._yearOfBirth, this._eyeColour, this._gender,
				this._hairColour, this._wizard);
	}
}