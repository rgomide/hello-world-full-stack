import { StatusBar } from 'expo-status-bar';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack'
import HomeScreen from './src/screens/HomeScreen';
import PeopleFormScreen from './src/screens/PeopleFormScreen';
import MusicFormScreen from './src/screens/MusicFormScreen';
const Stack = createNativeStackNavigator()

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Home">
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="PeopleForm" component={PeopleFormScreen} />
        <Stack.Screen name="MusicForm" component={MusicFormScreen} />
      </Stack.Navigator>
      <StatusBar style="auto" />
    </NavigationContainer>
  )
}

