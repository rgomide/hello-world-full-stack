import { StyleSheet, Text, View, TextInput, Button, FlatList, Platform } from 'react-native'
import { useEffect, useState } from 'react'
import axios from 'axios'

import * as DocumentPicker from 'expo-document-picker'
import { Audio } from 'expo-av'

const MUSIC_API_URL = 'http://localhost:8080/api/v1/musics'

const MusicFormScreen = () => {
  const [name, setName] = useState('')
  const [file, setFile] = useState('')
  const [musics, setMusics] = useState([])
  const [sound, setSound] = useState()

  const [fileDetails, setFileDetails] = useState(null)

  const loadData = async () => {
    const response = await axios.get(MUSIC_API_URL)
    setMusics(response.data)
  }

  useEffect(() => {
    const runLoadData = async () => { await loadData() }
    runLoadData()
  }, [])

  useEffect(() => {
    return () => {
      if (sound) {
        sound.unloadAsync()
      }
    }
  }, [sound])

  const insertMusic = async () => {
    if (!fileDetails) {
      alert('Please select a file')
      return
    }

    const fileUri = Platform.OS === 'ios' ? fileDetails.uri.replace('file://', '') : fileDetails.uri

    const fileInfo = {
      uri: fileUri,
      type: fileDetails.mimeType,
      name: fileDetails.name,
      size: fileDetails.size
    }

    await axios.post(
      MUSIC_API_URL,
      {
        name: name,
        file: fileInfo
      },
      {
        headers: {
          Accept: 'application/json'
        }
      }
    )

    await loadData()

    setName('')
    setFile('')
    setFileDetails(null)
  }

  const deleteMusic = async (id) => {
    await axios.delete(`${MUSIC_API_URL}/${id}`)
    await loadData()
  }

  const convertDateToString = (date) => {
    return new Date(date).toLocaleString()
  }

  const pickFile = async () => {
    try {
      const result = await DocumentPicker.getDocumentAsync({
        type: 'audio/*'
      })

      if (result.canceled) {
        return
      }

      const fileAsset = result.assets[0]
      setFileDetails(fileAsset)
      setFile(fileAsset.name)
    } catch (err) {
      console.error(err)
      alert('Error picking file')
    }
  }

  const playMusic = async (id) => {
    try {
      const uri = `${MUSIC_API_URL}/download/${id}`

      await Audio.setAudioModeAsync({
        allowsRecordingIOS: false,
        staysActiveInBackground: true,
        playsInSilentModeIOS: true,
        shouldDuckAndroid: true,
        playThroughEarpieceAndroid: false
      })

      const { sound: newSound } = await Audio.Sound.createAsync({ uri: uri }, { shouldPlay: false })

      setSound(newSound)

      await newSound.playAsync()
    } catch (error) {
      console.error('Error playing sound:', error)
      alert('Error playing audio')
    }
  }

  return (
    <View style={styles.container}>
      <View style={styles.form}>
        <Text style={styles.title}>Insert Music</Text>
        <TextInput
          style={styles.input}
          placeholder="Name"
          placeholderTextColor="gray"
          value={name}
          onChangeText={setName}
        />
        <View style={styles.fileInput}>
          <Text style={styles.fileName}>{file || 'No file selected'}</Text>
          <Button title="Choose File" onPress={pickFile} />
        </View>
        <Button
          title="Insert"
          onPress={() => {
            insertMusic()
          }}
        />
      </View>

      <FlatList
        style={styles.musicsExternal}
        contentContainerStyle={styles.musics}
        data={musics}
        renderItem={({ item }) => {
          return (
            <View style={styles.music}>
              <View style={styles.musicInfo}>
                <Text style={styles.musicName}>{item.name}</Text>
                <Text style={styles.musicCreatedAt}>{item.fileName}</Text>
                <Text style={styles.musicCreatedAt}>{convertDateToString(item.createdAt)}</Text>
              </View>
              <View style={styles.musicActions}>
                <Button
                  title="Play"
                  onPress={() => {
                    playMusic(item.id)
                  }}
                />
                <Button
                  title="Delete"
                  color="red"
                  onPress={() => {
                    deleteMusic(item.id)
                  }}
                />
              </View>
            </View>
          )
        }}
        keyExtractor={(item, index) => index.toString()}
      />
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
    padding: 10,
    gap: 10
  },
  form: {
    gap: 10,
    width: '100%'
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold'
  },
  input: {
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    padding: 10
  },
  musicsExternal: {
    width: '100%'
  },
  musics: {
    gap: 10,
    width: '100%',
    padding: 10
  },
  music: {
    padding: 10,
    width: '100%',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    gap: 5,
    borderWidth: 1,
    borderColor: 'gray'
  },
  musicActions: {
    flexDirection: 'row',
    gap: 5
  },
  musicInfo: {
    gap: 5
  },
  musicName: {
    fontSize: 16,
    fontWeight: 'bold'
  },
  musicCreatedAt: {
    fontSize: 12,
    color: 'gray'
  },
  fileInput: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    borderColor: 'gray',
    borderWidth: 1,
    padding: 10
  },
  fileName: {
    flex: 1,
    marginRight: 10
  }
})

export default MusicFormScreen
