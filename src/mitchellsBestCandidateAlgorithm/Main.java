package mitchellsBestCandidateAlgorithm;

public class Main {
	public static void main(String[] args) {
		MitchellsBestCandidateAlgorithm mitchellsBestCandidateAlgorithm = new MitchellsBestCandidateAlgorithm();
		mitchellsBestCandidateAlgorithm.setSize(1500).setMaximumPhase(200).setMaximumPointsPerPhase(50);
		mitchellsBestCandidateAlgorithm.nextPoints().stream()
									   .map(point -> "%s\t%s".formatted(point.getX(), point.getY()))
									   .forEach(System.out::println);
	}
}
