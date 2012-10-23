/*******************************************************************************
 * Copyright (c) 2012 OBiBa. All rights reserved.
 *  
 * This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0.
 *  
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.obiba.magma;

/**
 * A value loader allows deferred loading of values given a reference (file path, url etc.).
 */
public interface ValueLoader {

  /**
   * Check if the referred value is null without loading the value, in other words check if the reference is null.
   * @return
   */
  public boolean isNull();

  /**
   * Load the value from its reference.
   * @return
   */
  public Object getValue();

}
