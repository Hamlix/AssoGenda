package fr.paris10.projet.assogenda.assogenda.daos.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fr.paris10.projet.assogenda.assogenda.model.Association;

public class DAOFirebaseAssociation {

    private static DAOFirebaseAssociation instance;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference database;

    public DAOFirebaseAssociation() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference("association");
    }

    public static DAOFirebaseAssociation getInstance() {
        if(instance == null) {
            instance = new DAOFirebaseAssociation();
        }
        return instance;
    }

    public void createAssociation(String associationName, String associationUniversity, String associationDescription, String logo) {
        if(mFirebaseAuth.getCurrentUser() != null) {
            Association association = new Association(associationName, associationUniversity, associationDescription, mFirebaseUser.getUid(), logo);
            database.push().setValue(association);
        }
    }

    //TODO vérifier que le nom de l'association n'est pas déjà pris
    public boolean validateAssociationName(String associationName) {
        return associationName != null && !associationName.isEmpty() && associationName.length() >= 3;
    }

    public boolean validateAssociationUniversity(String associationUniversity) {
        return associationUniversity != null && !associationUniversity.isEmpty() && associationUniversity.length() >= 3;
    }

    public boolean validateAssociationDescription(String associationDescription) {
        return associationDescription != null && !associationDescription.isEmpty() && associationDescription.length() >= 3;
    }

    public boolean validateAssociation(String associationName, String associationUniversity, String associationDescription) {
        return validateAssociationName(associationName) && validateAssociationUniversity(associationUniversity)
                && validateAssociationDescription(associationDescription);
    }
}
