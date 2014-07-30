class FTage {
    enum eFTage {
            Neujahr(0),             /* FIX    */
            Erscheinungsfest(1),    /* FIX    */
            Karfreitag(2),          /* OS -2  */
            Ostersonntag(3),        /* GAUS   */
            Ostermontag(4),         /* OS +1  */
            Maifeiertag(5),         /* FIX    */
            ChrHimmelfahrt(6),      /* OS +39 */
            Pfingstmontag(7),       /* OS +50 */
            Fronleichnam(8),        /* OS +60 */
            MraHimmelfahrt(9),      /* FIX    */
            DtEinheit(10),          /* FIX    */
            Reformationstag(11),    /* FIX    */
            Allerheiligen(12),      /* FIX    */
            Weihnacht1(13),         /* FIX    */
            Weihnacht2(14)          /* FIX    */          
            eFTage(int value) {this.value = value}
            private final int value
            public int value() {return value}
    }
    enum eMonate {
            Januar(0),
            Februar(1),
            Maerz(2),
            April(3),
            Mai(4),
            Juni(5),
            Juli(6),
            August(7),
            September(8),
            Oktober(9),
            November(10),
            Dezember(11)
            eMonate(int value) {this.value = value}
            private final int value
            public int value() {return value}
    }

    /* constants  */
    def monate = ["Januar",
                  "Februar",
                  "Maerz",
                  "April",
                  "Mai",
                  "Juni",
                  "Juli",
                  "August",
                  "September",
                  "Oktober",
                  "November",
                  "Dezember"]
                  
    def feiertage = [
                  "Neujahr",
                  "Erscheinungsfest",
                  "Karfreitag",
                  "Ostersonntag",
                  "Ostermontag",
                  "Maifeiertag",
                  "ChrHimmelfahrt",
                  "Pfingstmontag",
                  "Fronleichnam",
                  "MraHimmelfahrt",
                  "DtEinheit",
                  "Reformationstag",
                  "Allerheiligen"]
    
    /* functions  */
    def initFTage() {
      fTage = new Date[15]
      for(int i=0; i<15; i++){
        fTage[i] = new Date()
        fTage[i].set(hourOfDay: 12, minute: 0, second: 0, year: 1980, month: 5-1, date: 7)
      }
    }//initFTage

    def setOsterSonntag(uJahr){
        def osDay
        def osMonth
        int a = uJahr % 19
        int b = uJahr %  4
        int c = uJahr %  7
        int k = uJahr / 100
        int q = k / 4
        int p = ((8 * k) + 13) / 25
        int Egz = (38 - (k - q) + p) % 30 // Die Jahrhundertepakte
        int M = (53 - Egz) % 30
        int N = (4 + k - q) % 7
        int d = ((19 * a) + M) % 30
        int e = ((2 * b) + (4 * c) + (6 * d) + N) % 7
        // Ausrechnen des Ostertermins:
        if ((22 + d + e) <= 31)
        {
          osDay = 22 + d + e
          osMonth = 3
        }
        else
        {
          osDay = d + e - 9
          osMonth = 4
          // Zwei Ausnahmen berücksichtigen:
          if (osDay == 26)
            osDay = 19
          else if ((osDay == 25) && (d == 28) && (a > 10))
            osDay = 18
        }
        currentYear = uJahr
        os = new Date()
        os.set(hourOfDay: 12, minute: 0, second: 0, year: uJahr, month: osMonth-1, date: osDay)
    }//setOsterSonntag
    def getOsterSonntag() { "OS: ${os}" }

    def setFixFTage() {
        fTage[eFTage.Neujahr.value()].set(hourOfDay: 12, minute: 0, second: 0, year: currentYear, month: eMonate.Januar.value(), date: 1)
        fTage[eFTage.Erscheinungsfest.value()].set(hourOfDay: 12, minute: 0, second: 0, year: currentYear, month: eMonate.Januar.value(), date: 6)
        fTage[eFTage.Maifeiertag.value()].set(hourOfDay: 12, minute: 0, second: 0, year: currentYear, month: eMonate.Mai.value(), date: 1)
        fTage[eFTage.MraHimmelfahrt.value()].set(hourOfDay: 12, minute: 0, second: 0, year: currentYear, month: eMonate.August.value(), date: 15)
        fTage[eFTage.DtEinheit.value()].set(hourOfDay: 12, minute: 0, second: 0, year: currentYear, month: eMonate.Oktober.value(), date: 3)
        fTage[eFTage.Reformationstag.value()].set(hourOfDay: 12, minute: 0, second: 0, year: currentYear, month: eMonate.Oktober.value(), date: 31)
        fTage[eFTage.Allerheiligen.value()].set(hourOfDay: 12, minute: 0, second: 0, year: currentYear, month: eMonate.November.value(), date: 1)
        fTage[eFTage.Weihnacht1.value()].set(hourOfDay: 12, minute: 0, second: 0, year: currentYear, month: eMonate.Dezember.value(), date: 25)
        fTage[eFTage.Weihnacht2.value()].set(hourOfDay: 12, minute: 0, second: 0, year: currentYear, month: eMonate.Dezember.value(), date: 26)
    }

    def setVarFTage() {
        fTage[eFTage.Karfreitag.value()] = os-2
        fTage[eFTage.Ostersonntag.value()] = os
        fTage[eFTage.Ostermontag.value()] = os+1
        fTage[eFTage.ChrHimmelfahrt.value()] = os+39
        fTage[eFTage.Pfingstmontag.value()] = os+50
        fTage[eFTage.Fronleichnam.value()] = os+60
    }

    def isFTag(testday) {
        for(int i=0; i<15; i++) {
          if(testday.clearTime() == fTage[i].clearTime())
             return i
        }
        return -1
    }

    def greet() { "${name}" }
    def getMonat(i) { monate[i] }
    def getFTag(i) { "${feiertage[i]} ${fTage[i]}"  }

    /* variables */
    def name
    def fTage 
    def os
    def currentYear
}//FTage    

def fTage = new FTage()
fTage.name = "German Feiertage"
println fTage.greet()
fTage.setOsterSonntag(2014)
println fTage.getOsterSonntag()
fTage.initFTage()
fTage.setVarFTage()
fTage.setFixFTage()
def testDate = new Date()
testDate.set(hourOfDay: 12, minute: 0, second: 0, year: 2014, month: 10-1, date: 3)
def ret = fTage.isFTag(testDate)
if(ret != -1){
  println "TESTDAY is FEIERTAG:"
  println fTage.getFTag(ret)
}
else
{
  println "TESTDAY IS NOT A FEIERTAG"
}

