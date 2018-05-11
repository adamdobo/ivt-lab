package hu.bme.mit.spaceship;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimary;
  private TorpedoStore mockSecondary;

  @Before
  public void init(){
    mockPrimary = mock(TorpedoStore.class);
    mockSecondary = mock(TorpedoStore.class);
    this.ship = new GT4500(mockPrimary, mockSecondary);

  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
      when(mockPrimary.fire(1)).thenReturn(true);
    // Act
      boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockPrimary.fire(mockPrimary.getTorpedoCount())).thenReturn(true);
    when(mockSecondary.fire(mockSecondary.getTorpedoCount())).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_Success_2(){
      // Arrange

      // Act
      ship.fireTorpedo(FiringMode.SINGLE);

      // Assert
      verify(mockPrimary).fire(1);
  }

    @Test
    public void fireTorpedo_All_Success_2(){
        // Arrange

        // Act
        ship.fireTorpedo(FiringMode.ALL);

        // Assert
        verify(mockPrimary).fire(mockPrimary.getTorpedoCount());
        verify(mockSecondary).fire(mockSecondary.getTorpedoCount());
    }

    @Test
    public void primaryFired(){
        // Arrange
        when(mockPrimary.fire(1)).thenReturn(true);
        // Act
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        // Assert
        assertEquals(true, result);
    }

    @Test
    public void secondaryFired(){
        when(mockSecondary.fire(1)).thenReturn(true);

        ship.fireTorpedo(FiringMode.SINGLE);
        boolean result = ship.fireTorpedo(FiringMode.SINGLE);

        assertEquals(true, result);
    }

    @Test
    public void bothEmpty(){
        when(mockPrimary.fire(1)).thenReturn(false);
        when(mockSecondary.fire(1)).thenReturn(false);

        boolean primResult = ship.fireTorpedo(FiringMode.SINGLE);
        boolean secResult = ship.fireTorpedo(FiringMode.SINGLE);

        assertEquals(false, primResult);
        assertEquals(false, secResult);
    }

    @Test
    public void fireOnlySec(){
        when(mockPrimary.isEmpty()).thenReturn(true);
        when(mockSecondary.isEmpty()).thenReturn(false);

        ship.fireTorpedo(FiringMode.ALL);

        verify(mockPrimary, never()).fire(mockPrimary.getTorpedoCount());
        verify(mockSecondary).fire(mockSecondary.getTorpedoCount());
    }

    @Test
    public void fireOnlyPrimary(){
        when(mockSecondary.isEmpty()).thenReturn(true);
        when(mockPrimary.isEmpty()).thenReturn(false);

        ship.fireTorpedo(FiringMode.ALL);

        verify(mockSecondary, never()).fire(mockSecondary.getTorpedoCount());
        verify(mockPrimary).fire(mockPrimary.getTorpedoCount());
    }

    @Test
    public void isEmptyAfterAllfire(){
        ship.fireTorpedo(FiringMode.ALL);

        assertEquals(0, mockPrimary.getTorpedoCount());
        assertEquals(0, mockSecondary.getTorpedoCount());
    }


}
