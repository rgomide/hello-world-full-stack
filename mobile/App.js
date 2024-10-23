import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View, TextInput, Button, FlatList } from 'react-native';
import { useEffect, useState } from 'react';
import axios from 'axios';

const PERSON_API_URL = 'http://localhost:8080/api/v1/people';

export default function App() {
  const [name, setName] = useState('');
  const [age, setAge] = useState('');
  const [people, setPeople] = useState([]);

  const loadData = async () => {
    const response = await axios.get(PERSON_API_URL);
    setPeople(response.data);
  };

  useEffect(() => {
    loadData();
  }, []);

  const insertPerson = async () => {
    const response = await axios.post(PERSON_API_URL, {
      name: name,
      age: age
    });

    await loadData();
  };

  const deletePerson = async (id) => {
    const response = await axios.delete(`${PERSON_API_URL}/${id}`);
    await loadData();
  };

  const convertDateToString = (date) => {
    return new Date(date).toLocaleString();
  };

  return (
    <View style={styles.container}>

      <View style={styles.form}>
        <Text style={styles.title}>Insert Person</Text>
        <TextInput style={styles.input} placeholder="Name" placeholderTextColor="gray" value={name} onChangeText={setName} />
        <TextInput style={styles.input} placeholder="Age" placeholderTextColor="gray" value={age} onChangeText={setAge} />
        <Button title="Insert" onPress={() => { insertPerson() }} />
      </View>


      <FlatList
        style={styles.peopleExternal}
        contentContainerStyle={styles.people}
        data={people}
        renderItem={({ item }) => {
          return (
            <View style={styles.person}>
              <View style={styles.personInfo}>
                <Text style={styles.personName}>{item.name} - {item.age}</Text>
                <Text style={styles.personCreatedAt}>{convertDateToString(item.createdAt)}</Text>
              </View>
              <Button title="Delete" color="red" onPress={() => { deletePerson(item.id) }} />
            </View>
          )
        }}
        keyExtractor={(item, index) => index.toString()}
      />

      <StatusBar style="auto" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
    padding: 10,
    gap: 10,
  },
  form: {
    gap: 10,
    width: '100%',
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
  },
  input: {
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    padding: 10,
  },
  peopleExternal: {
    width: '100%',
  },
  people: {
    gap: 10,
    width: '100%',
    padding: 10,
  },
  person: {
    padding: 10,
    width: '100%',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    gap: 5,
    borderWidth: 1,
    borderColor: 'gray',
  },
  personInfo: {
    gap: 5,
  },
  personName: {
    fontSize: 16,
    fontWeight: 'bold',
  },
  personCreatedAt: {
    fontSize: 12,
    color: 'gray',
  },
});
