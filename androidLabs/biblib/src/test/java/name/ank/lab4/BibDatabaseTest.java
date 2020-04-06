package name.ank.lab4;

import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BibDatabaseTest {

  private BibDatabase database;

  @Before
  public void setup() throws IOException {
    try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/references.bib"))) {
      database = new BibDatabase(reader);
    }
  }

  @Test
  public void getFirstEntry() {
    BibEntry first = database.getEntry(0);
    Assert.assertEquals(Types.ARTICLE, first.getType());
    Assert.assertEquals("The semantic web", first.getField(Keys.TITLE));
    Assert.assertNull("Field 'chapter' does not exist", first.getField(Keys.CHAPTER));
  }

  @Test
  public void normalModeDoesNotThrowException() {
    BibConfig cfg = database.getCfg();
    cfg.strict = false;

    BibEntry first = database.getEntry(0);
    for (int i = 0; i < cfg.maxValid + 1; i++) {
      BibEntry unused = database.getEntry(0);
      Assert.assertNotNull("Should not throw any exception @" + i, first.getType());
    }
  }

  @Test(expected = IllegalStateException.class)
  public void strictModeThrowsException() {
    BibConfig cfg = database.getCfg();
    cfg.strict = true;

    BibEntry first = database.getEntry(0);
    for (int i = 0; i < cfg.maxValid; i++) {
      BibEntry unused = database.getEntry(0);
    }
    first.getType();//should throw IllegalStateException
  }

  @Test
  public void shuffleFlag() throws IOException {
    BibConfig cfg = database.getCfg();
    cfg.shuffle = true;

    try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/referencesForShuffle.bib"))) {
      database = new BibDatabase(reader);
    }

    boolean check=false;
    BibEntry lastEntry=database.getEntry(0);

    for(int i=0; i<5;i++){
      try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/referencesForShuffle.bib"))) {
        database = new BibDatabase(reader);
      }

      if(database.getEntry(0).getType()!=lastEntry.getType()) check=true;
      lastEntry=database.getEntry(0);
    }
    Assert.assertTrue(check);
  }
}
