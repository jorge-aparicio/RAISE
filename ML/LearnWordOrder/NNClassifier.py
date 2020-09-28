import pandas as pd
from sklearn import svm
from sklearn.neural_network import MLPClassifier
from sklearn.preprocessing import StandardScaler, LabelEncoder
from sklearn.model_selection import train_test_split
from sklearn.metrics import confusion_matrix, classification_report

# read and format the data
wordOrderData = pd.read_csv("data/wordOrderFeatures.txt", sep = '@')
label_was_replaced= LabelEncoder()
wordOrderData['was_replaced'] = label_was_replaced.fit_transform(wordOrderData['was_replaced'])
print(wordOrderData['was_replaced'].value_counts())


# separate dataset into features and labels
X = wordOrderData.drop('was_replaced', axis=1)
X = X.drop('original_word' , axis=1)
X = X.drop('replaced_word' , axis=1)
X = X.drop( 'options_list', axis=1)
y = wordOrderData['was_replaced']

#split the data between test and train
X_train, X_test, y_train, y_test = train_test_split(X,y, test_size = 0.2, random_state = 22)

# apply scaling
sc = StandardScaler()
X_train = sc.fit_transform(X_train)
X_test = sc.transform(X_test)


# Nerual Net Classifier
clf = MLPClassifier(hidden_layer_sizes=(11,11,11), max_iter=2000)

clf.fit(X_train, y_train)

pred_MLP = clf.predict(X_test)

print(classification_report(y_test, pred_MLP))


# Support Vector Machine
clf = svm.SVC(probability=True)

clf.fit(X_train, y_train)
pred_SVM = clf.predict(X_test)
conf_SVM = clf.predict_proba(X_test)
print(classification_report(y_test, pred_SVM))