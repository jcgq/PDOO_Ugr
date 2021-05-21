package GUI;

import civitas.Casilla;
import civitas.CasillaCalle;


public class CasillaPanel extends javax.swing.JPanel {

    Casilla casilla;
    
    public CasillaPanel() {
        initComponents();
    }

    void setCasilla(Casilla _casilla){
        casilla = _casilla;
        nombre.setText(_casilla.getNombre());
        otraInfo.setText(_casilla.toString());
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vistaCasilla = new javax.swing.JLabel();
        nombre = new javax.swing.JLabel();
        otraInfo = new javax.swing.JLabel();
        textNombre = new javax.swing.JTextField();
        textInfo = new javax.swing.JTextField();

        vistaCasilla.setText("VistaCasilla");
        vistaCasilla.setEnabled(false);

        nombre.setText("Nombre");
        nombre.setEnabled(false);

        otraInfo.setText("otraInfo");
        otraInfo.setEnabled(false);

        textNombre.setEnabled(false);

        textInfo.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(vistaCasilla))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombre)
                            .addComponent(otraInfo))
                        .addGap(74, 74, 74)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(204, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(vistaCasilla)
                .addGap(81, 81, 81)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombre)
                    .addComponent(textNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(otraInfo)
                    .addComponent(textInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(99, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel nombre;
    private javax.swing.JLabel otraInfo;
    private javax.swing.JTextField textInfo;
    private javax.swing.JTextField textNombre;
    private javax.swing.JLabel vistaCasilla;
    // End of variables declaration//GEN-END:variables
}
