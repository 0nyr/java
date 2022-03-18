import java.util.TimerTask;

public class Task extends TimerTask {
	private RollingFrame rf ;
	private SplashScreen sp ;
	private Sync sync ;
	private int AInb, gamePhase ;
	
	public Task(RollingFrame rf){
		super() ;
		this.rf=rf ;
	}
	
	public Task(SplashScreen sp){
		super() ;
		this.sp=sp ;
	}
	
	public Task(Sync sync, int AInb, int gamePhase){
		super() ;
		this.sync=sync ;
		this.AInb=AInb ;
		this.gamePhase=gamePhase ;
	}

	@Override
	public void run(){
		if(rf!=null){
			rf.secondStep() ;
			rf=null;
		}
		if(sp!=null){
			sp.closeSplashScreen() ;
			sp=null;
		}
		if(sync!=null){
			if(AInb>0){
				AITasks(AInb) ;
			}
		}
	}
	
	private void AITasks(int AInb) {
		//Check le niveau de l'IA
		switch (AInb) {
			case 1 :
				//Check la phase pendant laquelle la tâche planifiée est demandée afin d'y executer les bonnes methodes
				switch (gamePhase) {
					case 0 :
						sync.gamePhase0AI1Tasks() ;
						break;
					case 1 :
						sync.gamePhase1AI1Tasks();
						break;
					case 3 :
						sync.gamePhase3AI1Tasks();
						break;
					case 4 :
						sync.gamePhase4AI1Task();
						break;
				}
				break ;

			case 2 :
				break;

			case 3 :
				break;
		}
	}
}
