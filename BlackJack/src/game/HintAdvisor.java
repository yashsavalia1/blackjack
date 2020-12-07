package game;

public class HintAdvisor {
  public String getHint(int score, boolean hasHighAces) {
    if (score <= 10)
      return "You should definitely hit!"; 
    if (score == 11)
      return "Yeah probably hit"; 
    if (score <= 13)
      return "I doubt you'll win with that"; 
    if (score == 14 && hasHighAces)
      return "Always hit on a soft 14"; 
    if (score <= 15)
      return "You could still hit"; 
    if (score <= 17)
      return "Probably stay"; 
    if (score <= 20)
      return "Don't hit"; 
    if (score == 21)
      return "DO NOT HIT"; 
    return "";
  }
}
