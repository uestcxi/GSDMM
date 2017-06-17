package main;
import java.util.HashMap;

public class GSDMM
{
	int K;
	double alpha;
	double beta;
	int iterNum;
	String dataset;
	
	HashMap<String, Integer> wordToIdMap;
	int V;
	DocumentSet documentSet;
	String dataDir = "data/"; 
	String outputPath = "result/";
	
	public GSDMM(int K, double alpha, double beta, int iterNum, String dataset)
	{
		this.K = K;
		this.alpha = alpha;
		this.beta = beta;
		this.iterNum = iterNum;
		this.dataset = dataset;
		this.wordToIdMap = new HashMap<String, Integer>();
	}
	public static void main(String args[]) throws Exception
	{
        if (args.length != 5) {
            System.err.println("Usage:\n" +
                "  java -cp gsdmm.jar main.GSDMM k alpha beta iterNum dataset\n\n" +
                "  java -cp gsdmm.jar main.GSDMM 50 0.1 0.1 10 20ng\n");
            return;
        }

		int K = Integer.parseInt(args[0]);
		double alpha = Double.valueOf(args[1]);
		double beta = Double.valueOf(args[2]);
		int iterNum = Integer.parseInt(args[3]);
		String dataset = args[4];
		GSDMM gsdmm = new GSDMM(K, alpha, beta, iterNum, dataset);
		
		long startTime = System.currentTimeMillis();				
		gsdmm.getDocuments();
		long endTime = System.currentTimeMillis();
		System.out.println("getDocuments Time Used:" + (endTime-startTime)/1000.0 + "s");
		
		startTime = System.currentTimeMillis();	
		gsdmm.runGSDMM();
		endTime = System.currentTimeMillis();
		System.out.println("gibbsSampling Time Used:" + (endTime-startTime)/1000.0 + "s");
	}
	
	public void getDocuments() throws Exception
	{
		documentSet = new DocumentSet(dataDir + dataset, wordToIdMap);
		V = wordToIdMap.size();
	}
	
	public void runGSDMM() throws Exception
	{
		String ParametersStr = "K"+K+"iterNum"+ iterNum +"alpha" + String.format("%.3f", alpha)
								+ "beta" + String.format("%.3f", beta);
		Model model = new Model(K, V, iterNum,alpha, beta, dataset,  ParametersStr);
		model.intialize(documentSet);
		model.gibbsSampling(documentSet);
		model.output(documentSet, outputPath);
	}
}
