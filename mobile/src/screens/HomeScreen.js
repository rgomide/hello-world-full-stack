import React from 'react'
import { StyleSheet, View, Button } from 'react-native'

const HomeScreen = (props) => {
  const navigation = props.navigation

  return (
    <View style={styles.mainContainer}>
      <Button
        onPress={() => {
          navigation.navigate('PeopleForm')
        }}
        title="Go to People Form" />
      <Button
        onPress={() => {
          navigation.navigate('MusicForm')
        }}
        title="Go to Music Form" />
    </View>
  )
}

const styles = StyleSheet.create({
  mainContainer: {
    padding: 10,
    flex: 1,
    justifyContent: 'center',
    gap: 10
  }
})

export default HomeScreen